// Configuración de API
const API_BASE_URL = 'http://localhost:8080/api';
let turnos = [];
let pacientes = [];
let odontologos = [];
let currentEditId = null;

// Función para hacer peticiones HTTP
async function apiRequest(endpoint, method = 'GET', data = null) {
    const options = {
        method,
        headers: {
            'Content-Type': 'application/json',
        }
    };
    
    if (data) {
        options.body = JSON.stringify(data);
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
        if (!response.ok) {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
        // Si la respuesta está vacía (como en DELETE 204), retornar null
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            const text = await response.text();
            return text ? JSON.parse(text) : null;
        }
        return null;
    } catch (error) {
        console.error('Error en la petición:', error);
        alert(`Error: ${error.message}`);
        throw error;
    }
}

// Cargar turnos desde el backend
async function loadTurnos() {
    try {
        turnos = await apiRequest('/turno');
        renderTable();
        updateStats();
    } catch (error) {
        console.error('Error al cargar turnos:', error);
    }
}

// Cargar pacientes desde el backend
async function loadPacientes() {
    try {
        pacientes = await apiRequest('/paciente');
    } catch (error) {
        console.error('Error al cargar pacientes:', error);
    }
}

// Cargar odontólogos desde el backend
async function loadOdontologos() {
    try {
        odontologos = await apiRequest('/odontologo');
    } catch (error) {
        console.error('Error al cargar odontólogos:', error);
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', async () => {
    await loadPacientes();
    await loadOdontologos();
    await loadTurnos();
});

// Cargar pacientes y odontólogos en selects
async function loadSelects() {
    await loadPacientes();
    await loadOdontologos();
    
    const createPacienteSelect = document.getElementById('createPacienteId');
    const editPacienteSelect = document.getElementById('editPacienteId');
    const createOdontologoSelect = document.getElementById('createOdontologoId');
    const editOdontologoSelect = document.getElementById('editOdontologoId');
    
    if (createPacienteSelect) {
        createPacienteSelect.innerHTML = '<option value="">Selecciona un paciente</option>';
        pacientes.forEach(paciente => {
            const option = document.createElement('option');
            option.value = paciente.id;
            option.textContent = `${paciente.nombre} ${paciente.apellido}`;
            createPacienteSelect.appendChild(option);
        });
    }
    
    if (editPacienteSelect) {
        editPacienteSelect.innerHTML = '<option value="">Selecciona un paciente</option>';
        pacientes.forEach(paciente => {
            const option = document.createElement('option');
            option.value = paciente.id;
            option.textContent = `${paciente.nombre} ${paciente.apellido}`;
            editPacienteSelect.appendChild(option);
        });
    }
    
    if (createOdontologoSelect) {
        createOdontologoSelect.innerHTML = '<option value="">Selecciona un odontólogo</option>';
        odontologos.forEach(odontologo => {
            const option = document.createElement('option');
            option.value = odontologo.id;
            option.textContent = `Dr. ${odontologo.nombre} ${odontologo.apellido}`;
            createOdontologoSelect.appendChild(option);
        });
    }
    
    if (editOdontologoSelect) {
        editOdontologoSelect.innerHTML = '<option value="">Selecciona un odontólogo</option>';
        odontologos.forEach(odontologo => {
            const option = document.createElement('option');
            option.value = odontologo.id;
            option.textContent = `Dr. ${odontologo.nombre} ${odontologo.apellido}`;
            editOdontologoSelect.appendChild(option);
        });
    }
}

// Renderizar tabla
function renderTable() {
    const tbody = document.getElementById('turnosTable');
    
    if (turnos.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                    No hay turnos registrados
                </td>
            </tr>
        `;
        return;
    }
    
    // Ordenar por fecha (más recientes primero)
    const sortedTurnos = [...turnos].sort((a, b) => new Date(b.fecha) - new Date(a.fecha));
    
    tbody.innerHTML = sortedTurnos.map(turno => {
        const paciente = pacientes.find(p => p.id === turno.pacienteId);
        const odontologo = odontologos.find(o => o.id === turno.odontologoId);
        
        if (!paciente || !odontologo) return '';
        
        const fecha = new Date(turno.fecha).toLocaleDateString('es-AR', {
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });
        
        const pacienteInitials = paciente.nombre.charAt(0) + paciente.apellido.charAt(0);
        const odontologoInitials = odontologo.nombre.charAt(0) + odontologo.apellido.charAt(0);
        
        return `
            <tr>
                <td style="font-weight: 600;">${turno.id}</td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--text-secondary);">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        <span style="font-weight: 500;">${fecha}</span>
                    </div>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <div class="avatar" style="background: rgba(6, 182, 212, 0.1); color: var(--accent-cyan);">
                            ${pacienteInitials}
                        </div>
                        <div>
                            <div style="font-weight: 500;">${paciente.nombre} ${paciente.apellido}</div>
                            <div style="font-size: 12px; color: var(--text-secondary);">${paciente.email}</div>
                        </div>
                    </div>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <div class="avatar" style="background: rgba(16, 185, 129, 0.1); color: var(--accent-emerald);">
                            ${odontologoInitials}
                        </div>
                        <div>
                            <div style="font-weight: 500;">Dr. ${odontologo.nombre} ${odontologo.apellido}</div>
                            <div style="font-size: 12px; color: var(--text-secondary);">${odontologo.matricula}</div>
                        </div>
                    </div>
                </td>
                <td class="text-right">
                    <div class="action-buttons">
                        <button class="btn btn-ghost" onclick="editTurno(${turno.id})" title="Editar">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                                <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                            </svg>
                        </button>
                        <button class="btn btn-ghost btn-danger" onclick="deleteTurno(${turno.id})" title="Eliminar">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <polyline points="3 6 5 6 21 6"></polyline>
                                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                            </svg>
                        </button>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

// Actualizar estadísticas
function updateStats() {
    document.getElementById('totalTurnos').textContent = turnos.length;
    document.getElementById('pacientesActivos').textContent = pacientes.length;
}

// Guardar en localStorage (ya no se usa, pero se mantiene por compatibilidad)
function saveTurnos() {
    // Ya no se usa localStorage
}

// Abrir modal crear
async function openCreateModal() {
    await loadSelects();
    document.getElementById('createModal').classList.add('active');
    document.getElementById('createPacienteId').value = '';
    document.getElementById('createOdontologoId').value = '';
    document.getElementById('createFecha').value = '';
}

// Cerrar modal crear
function closeCreateModal() {
    document.getElementById('createModal').classList.remove('active');
}

// Crear turno
async function createTurno() {
    const pacienteId = parseInt(document.getElementById('createPacienteId').value);
    const odontologoId = parseInt(document.getElementById('createOdontologoId').value);
    const fecha = document.getElementById('createFecha').value;
    
    if (!pacienteId || !odontologoId || !fecha) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        // El input date devuelve "YYYY-MM-DD", que es el formato correcto
        const nuevoTurno = {
            pacienteId,
            odontologoId,
            fecha: fecha
        };
        
        await apiRequest('/turno', 'POST', nuevoTurno);
        await loadTurnos();
        closeCreateModal();
    } catch (error) {
        console.error('Error al crear turno:', error);
    }
}

// Editar turno
async function editTurno(id) {
    const turno = turnos.find(t => t.id === id);
    if (!turno) return;
    
    await loadSelects();
    currentEditId = id;
    document.getElementById('editPacienteId').value = turno.pacienteId;
    document.getElementById('editOdontologoId').value = turno.odontologoId;
    
    // Convertir fecha a formato date (YYYY-MM-DD)
    let fechaDate = '';
    if (turno.fecha) {
        try {
            // Si la fecha viene en formato ISO con hora, extraer solo la parte de fecha
            const fechaStr = turno.fecha.toString();
            // Tomar solo los primeros 10 caracteres (YYYY-MM-DD)
            fechaDate = fechaStr.substring(0, 10);
            
            // Validar que tenga el formato correcto
            if (!/^\d{4}-\d{2}-\d{2}$/.test(fechaDate)) {
                // Si no tiene el formato correcto, intentar parsearlo
                const fecha = new Date(turno.fecha);
                if (!isNaN(fecha.getTime())) {
                    const year = fecha.getFullYear();
                    const month = String(fecha.getMonth() + 1).padStart(2, '0');
                    const day = String(fecha.getDate()).padStart(2, '0');
                    fechaDate = `${year}-${month}-${day}`;
                }
            }
        } catch (e) {
            console.error('Error al convertir fecha:', e);
            // Fallback: tomar los primeros 10 caracteres
            fechaDate = turno.fecha.toString().substring(0, 10);
        }
    }
    
    document.getElementById('editFecha').value = fechaDate;
    document.getElementById('editModal').classList.add('active');
}

// Cerrar modal editar
function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
    currentEditId = null;
}

// Actualizar turno
async function updateTurno() {
    if (!currentEditId) return;
    
    const pacienteId = parseInt(document.getElementById('editPacienteId').value);
    const odontologoId = parseInt(document.getElementById('editOdontologoId').value);
    const fecha = document.getElementById('editFecha').value;
    
    if (!pacienteId || !odontologoId || !fecha) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        // El input date devuelve "YYYY-MM-DD", que es el formato correcto
        const turnoActualizado = {
            pacienteId,
            odontologoId,
            fecha: fecha
        };
        
        await apiRequest(`/turno/${currentEditId}`, 'PUT', turnoActualizado);
        await loadTurnos();
        closeEditModal();
    } catch (error) {
        console.error('Error al actualizar turno:', error);
    }
}

// Eliminar turno
async function deleteTurno(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este turno?')) return;
    
    try {
        await apiRequest(`/turno/${id}`, 'DELETE');
        await loadTurnos();
    } catch (error) {
        console.error('Error al eliminar turno:', error);
    }
}

// Cerrar modales al hacer clic fuera
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

