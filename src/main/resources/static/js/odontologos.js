// Configuración de API
const API_BASE_URL = 'http://localhost:8080/api';
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

// Cargar odontólogos desde el backend
async function loadOdontologos() {
    try {
        odontologos = await apiRequest('/odontologo');
        renderTable();
        updateCount();
    } catch (error) {
        console.error('Error al cargar odontólogos:', error);
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
    loadOdontologos();
});

// Renderizar tabla
function renderTable() {
    const tbody = document.getElementById('odontologosTable');
    
    if (odontologos.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="4" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                    No hay odontólogos registrados
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = odontologos.map(odontologo => {
        const initials = odontologo.nombre.charAt(0) + odontologo.apellido.charAt(0);
        
        return `
            <tr>
                <td style="font-weight: 600;">${odontologo.id}</td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <div class="avatar" style="background: rgba(16, 185, 129, 0.1); color: var(--accent-emerald);">
                            ${initials}
                        </div>
                        <span style="font-weight: 500;">${odontologo.nombre} ${odontologo.apellido}</span>
                    </div>
                </td>
                <td>
                    <span class="badge badge-outline" style="font-family: monospace;">${odontologo.matricula}</span>
                </td>
                <td class="text-right">
                    <div class="action-buttons">
                        <button class="btn btn-ghost" onclick="editOdontologo(${odontologo.id})" title="Editar">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                                <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                            </svg>
                        </button>
                        <button class="btn btn-ghost btn-danger" onclick="deleteOdontologo(${odontologo.id})" title="Eliminar">
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
    document.getElementById('totalCount').textContent = odontologos.length;
}

// Guardar en localStorage (ya no se usa, pero se mantiene por compatibilidad)
function saveOdontologos() {
    // Ya no se usa localStorage
}

// Abrir modal crear
function openCreateModal() {
    document.getElementById('createModal').classList.add('active');
    document.getElementById('createNombre').value = '';
    document.getElementById('createApellido').value = '';
    document.getElementById('createMatricula').value = '';
}

// Cerrar modal crear
function closeCreateModal() {
    document.getElementById('createModal').classList.remove('active');
}

// Crear odontólogo
async function createOdontologo() {
    const nombre = document.getElementById('createNombre').value.trim();
    const apellido = document.getElementById('createApellido').value.trim();
    const matricula = document.getElementById('createMatricula').value.trim();
    
    if (!nombre || !apellido || !matricula) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const nuevoOdontologo = {
            nombre,
            apellido,
            matricula
        };
        
        await apiRequest('/odontologo', 'POST', nuevoOdontologo);
        await loadOdontologos();
        closeCreateModal();
    } catch (error) {
        console.error('Error al crear odontólogo:', error);
    }
}

// Editar odontólogo
function editOdontologo(id) {
    const odontologo = odontologos.find(o => o.id === id);
    if (!odontologo) return;
    
    currentEditId = id;
    document.getElementById('editNombre').value = odontologo.nombre;
    document.getElementById('editApellido').value = odontologo.apellido;
    document.getElementById('editMatricula').value = odontologo.matricula;
    document.getElementById('editModal').classList.add('active');
}

// Cerrar modal editar
function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
    currentEditId = null;
}

// Actualizar odontólogo
async function updateOdontologo() {
    if (!currentEditId) return;
    
    const nombre = document.getElementById('editNombre').value.trim();
    const apellido = document.getElementById('editApellido').value.trim();
    const matricula = document.getElementById('editMatricula').value.trim();
    
    if (!nombre || !apellido || !matricula) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const odontologoActualizado = {
            nombre,
            apellido,
            matricula
        };
        
        await apiRequest(`/odontologo/${currentEditId}`, 'PUT', odontologoActualizado);
        await loadOdontologos();
        closeEditModal();
    } catch (error) {
        console.error('Error al actualizar odontólogo:', error);
    }
}

// Eliminar odontólogo
async function deleteOdontologo(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este odontólogo?')) return;
    
    try {
        await apiRequest(`/odontologo/${id}`, 'DELETE');
        await loadOdontologos();
    } catch (error) {
        console.error('Error al eliminar odontólogo:', error);
    }
}

// Cerrar modales al hacer clic fuera
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

