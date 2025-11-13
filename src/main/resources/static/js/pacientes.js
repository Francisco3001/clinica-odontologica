// Configuración de API
const API_BASE_URL = 'http://localhost:8080/api';
let pacientes = [];
let domicilios = [];
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

// Cargar pacientes desde el backend
async function loadPacientes() {
    try {
        pacientes = await apiRequest('/paciente');
        renderTable();
        updateCount();
    } catch (error) {
        console.error('Error al cargar pacientes:', error);
    }
}

// Cargar domicilios desde el backend
async function loadDomicilios() {
    try {
        domicilios = await apiRequest('/domicilio');
        const createSelect = document.getElementById('createDomicilioId');
        const editSelect = document.getElementById('editDomicilioId');
        
        if (createSelect) {
            createSelect.innerHTML = '<option value="">Selecciona un domicilio</option>';
            domicilios.forEach(domicilio => {
                const option = document.createElement('option');
                option.value = domicilio.id;
                option.textContent = `${domicilio.calle} ${domicilio.numero}, ${domicilio.localidad}`;
                createSelect.appendChild(option);
            });
        }
        
        if (editSelect) {
            editSelect.innerHTML = '<option value="">Selecciona un domicilio</option>';
            domicilios.forEach(domicilio => {
                const option = document.createElement('option');
                option.value = domicilio.id;
                option.textContent = `${domicilio.calle} ${domicilio.numero}, ${domicilio.localidad}`;
                editSelect.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error al cargar domicilios:', error);
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', async () => {
    await loadDomicilios();
    await loadPacientes();
});

// La función loadDomicilios ahora está arriba y carga desde el backend

// Renderizar tabla
function renderTable() {
    const tbody = document.getElementById('pacientesTable');
    
    if (pacientes.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                    No hay pacientes registrados
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = pacientes.map(paciente => {
        const domicilio = domicilios.find(d => d.id === paciente.domicilioId);
        const domicilioText = domicilio ? `${domicilio.calle} ${domicilio.numero}` : 'N/A';
        const fecha = new Date(paciente.fechaIngreso).toLocaleDateString('es-AR');
        const initials = paciente.nombre.charAt(0) + paciente.apellido.charAt(0);
        
        return `
            <tr>
                <td style="font-weight: 600;">${paciente.id}</td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <div class="avatar" style="background: rgba(6, 182, 212, 0.1); color: var(--accent-cyan);">
                            ${initials}
                        </div>
                        <div>
                            <div style="font-weight: 500;">${paciente.nombre} ${paciente.apellido}</div>
                            <div style="font-size: 12px; color: var(--text-secondary); display: flex; align-items: center; gap: 4px;">
                                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                                    <polyline points="22,6 12,13 2,6"></polyline>
                                </svg>
                                ${paciente.email}
                            </div>
                        </div>
                    </div>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 4px; font-size: 14px;">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
                        </svg>
                        ${paciente.numeroContacto}
                    </div>
                </td>
                <td>
                    <div style="display: flex; align-items: start; gap: 4px; font-size: 14px; color: var(--text-secondary);">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="margin-top: 2px;">
                            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                            <circle cx="12" cy="10" r="3"></circle>
                        </svg>
                        ${domicilioText}
                    </div>
                </td>
                <td>
                    <span class="badge badge-outline">${fecha}</span>
                </td>
                <td class="text-right">
                    <div class="action-buttons">
                        <button class="btn btn-ghost" onclick="editPaciente(${paciente.id})" title="Editar">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                                <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                            </svg>
                        </button>
                        <button class="btn btn-ghost btn-danger" onclick="deletePaciente(${paciente.id})" title="Eliminar">
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

// Actualizar contador
function updateCount() {
    document.getElementById('totalCount').textContent = pacientes.length;
}

// Guardar en localStorage (ya no se usa, pero se mantiene por compatibilidad)
function savePacientes() {
    // Ya no se usa localStorage
}

// Abrir modal crear
async function openCreateModal() {
    await loadDomicilios();
    document.getElementById('createModal').classList.add('active');
    document.getElementById('createNombre').value = '';
    document.getElementById('createApellido').value = '';
    document.getElementById('createEmail').value = '';
    document.getElementById('createNumeroContacto').value = '';
    document.getElementById('createFechaIngreso').value = '';
    document.getElementById('createDomicilioId').value = '';
}

// Cerrar modal crear
function closeCreateModal() {
    document.getElementById('createModal').classList.remove('active');
}

// Crear paciente
async function createPaciente() {
    const nombre = document.getElementById('createNombre').value.trim();
    const apellido = document.getElementById('createApellido').value.trim();
    const email = document.getElementById('createEmail').value.trim();
    const numeroContacto = document.getElementById('createNumeroContacto').value.trim();
    const fechaIngreso = document.getElementById('createFechaIngreso').value;
    const domicilioId = parseInt(document.getElementById('createDomicilioId').value);
    
    if (!nombre || !apellido || !email || !numeroContacto || !fechaIngreso || !domicilioId) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const nuevoPaciente = {
            nombre,
            apellido,
            email,
            numeroContacto: parseInt(numeroContacto),
            fechaIngreso,
            domicilioId: domicilioId
        };
        
        await apiRequest('/paciente', 'POST', nuevoPaciente);
        await loadPacientes();
        closeCreateModal();
    } catch (error) {
        console.error('Error al crear paciente:', error);
    }
}

// Editar paciente
async function editPaciente(id) {
    const paciente = pacientes.find(p => p.id === id);
    if (!paciente) return;
    
    await loadDomicilios();
    currentEditId = id;
    document.getElementById('editNombre').value = paciente.nombre;
    document.getElementById('editApellido').value = paciente.apellido;
    document.getElementById('editEmail').value = paciente.email;
    document.getElementById('editNumeroContacto').value = paciente.numeroContacto;
    document.getElementById('editFechaIngreso').value = paciente.fechaIngreso;
    document.getElementById('editDomicilioId').value = paciente.domicilioId;
    document.getElementById('editModal').classList.add('active');
}

// Cerrar modal editar
function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
    currentEditId = null;
}

// Actualizar paciente
async function updatePaciente() {
    if (!currentEditId) return;
    
    const nombre = document.getElementById('editNombre').value.trim();
    const apellido = document.getElementById('editApellido').value.trim();
    const email = document.getElementById('editEmail').value.trim();
    const numeroContacto = document.getElementById('editNumeroContacto').value.trim();
    const fechaIngreso = document.getElementById('editFechaIngreso').value;
    const domicilioId = parseInt(document.getElementById('editDomicilioId').value);
    
    if (!nombre || !apellido || !email || !numeroContacto || !fechaIngreso || !domicilioId) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const pacienteActualizado = {
            nombre,
            apellido,
            email,
            numeroContacto: parseInt(numeroContacto),
            fechaIngreso,
            domicilioId: domicilioId
        };
        
        await apiRequest(`/paciente/${currentEditId}`, 'PUT', pacienteActualizado);
        await loadPacientes();
        closeEditModal();
    } catch (error) {
        console.error('Error al actualizar paciente:', error);
    }
}

// Eliminar paciente
async function deletePaciente(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este paciente?')) return;
    
    try {
        await apiRequest(`/paciente/${id}`, 'DELETE');
        await loadPacientes();
    } catch (error) {
        console.error('Error al eliminar paciente:', error);
    }
}

// Cerrar modales al hacer clic fuera
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

