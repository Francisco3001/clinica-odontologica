// Configuración de API
const API_BASE_URL = 'http://localhost:8080/api';
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

// Cargar domicilios desde el backend
async function loadDomicilios() {
    try {
        domicilios = await apiRequest('/domicilio');
        renderTable();
        updateCount();
    } catch (error) {
        console.error('Error al cargar domicilios:', error);
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
    loadDomicilios();
});

// Renderizar tabla
function renderTable() {
    const tbody = document.getElementById('domiciliosTable');
    
    if (domicilios.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                    No hay domicilios registrados
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = domicilios.map(domicilio => `
        <tr>
            <td style="font-weight: 600;">${domicilio.id}</td>
            <td>${domicilio.calle}</td>
            <td>${domicilio.numero}</td>
            <td>${domicilio.localidad}</td>
            <td>${domicilio.provincia}</td>
            <td class="text-right">
                <div class="action-buttons">
                    <button class="btn btn-ghost" onclick="editDomicilio(${domicilio.id})" title="Editar">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                        </svg>
                    </button>
                    <button class="btn btn-ghost btn-danger" onclick="deleteDomicilio(${domicilio.id})" title="Eliminar">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                        </svg>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

// Actualizar contador
function updateCount() {
    document.getElementById('totalCount').textContent = domicilios.length;
}

// Guardar en localStorage (ya no se usa, pero se mantiene por compatibilidad)
function saveDomicilios() {
    // Ya no se usa localStorage
}

// Abrir modal crear
function openCreateModal() {
    document.getElementById('createModal').classList.add('active');
    document.getElementById('createCalle').value = '';
    document.getElementById('createNumero').value = '';
    document.getElementById('createLocalidad').value = '';
    document.getElementById('createProvincia').value = '';
}

// Cerrar modal crear
function closeCreateModal() {
    document.getElementById('createModal').classList.remove('active');
}

// Crear domicilio
async function createDomicilio() {
    const calle = document.getElementById('createCalle').value.trim();
    const numero = document.getElementById('createNumero').value.trim();
    const localidad = document.getElementById('createLocalidad').value.trim();
    const provincia = document.getElementById('createProvincia').value.trim();
    
    if (!calle || !numero || !localidad || !provincia) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const nuevoDomicilio = {
            calle,
            numero: parseInt(numero),
            localidad,
            provincia
        };
        
        await apiRequest('/domicilio', 'POST', nuevoDomicilio);
        await loadDomicilios();
        closeCreateModal();
    } catch (error) {
        console.error('Error al crear domicilio:', error);
    }
}

// Editar domicilio
function editDomicilio(id) {
    const domicilio = domicilios.find(d => d.id === id);
    if (!domicilio) return;
    
    currentEditId = id;
    document.getElementById('editCalle').value = domicilio.calle;
    document.getElementById('editNumero').value = domicilio.numero;
    document.getElementById('editLocalidad').value = domicilio.localidad;
    document.getElementById('editProvincia').value = domicilio.provincia;
    document.getElementById('editModal').classList.add('active');
}

// Cerrar modal editar
function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
    currentEditId = null;
}

// Actualizar domicilio
async function updateDomicilio() {
    if (!currentEditId) return;
    
    const calle = document.getElementById('editCalle').value.trim();
    const numero = document.getElementById('editNumero').value.trim();
    const localidad = document.getElementById('editLocalidad').value.trim();
    const provincia = document.getElementById('editProvincia').value.trim();
    
    if (!calle || !numero || !localidad || !provincia) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const domicilioActualizado = {
            calle,
            numero: parseInt(numero),
            localidad,
            provincia
        };
        
        await apiRequest(`/domicilio/${currentEditId}`, 'PUT', domicilioActualizado);
        await loadDomicilios();
        closeEditModal();
    } catch (error) {
        console.error('Error al actualizar domicilio:', error);
    }
}

// Eliminar domicilio
async function deleteDomicilio(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este domicilio?')) return;
    
    try {
        await apiRequest(`/domicilio/${id}`, 'DELETE');
        await loadDomicilios();
    } catch (error) {
        console.error('Error al eliminar domicilio:', error);
    }
}

// Cerrar modales al hacer clic fuera
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

