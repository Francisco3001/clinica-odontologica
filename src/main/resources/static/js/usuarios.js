// Configuración de API
const API_BASE_URL = 'http://localhost:8080/api';
let usuarios = [];
let currentEditId = null;

const roles = ['ADMIN', 'USUARIO'];

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

// Cargar usuarios desde el backend
async function loadUsuarios() {
    try {
        usuarios = await apiRequest('/usuario');
        renderTable();
        updateStats();
    } catch (error) {
        console.error('Error al cargar usuarios:', error);
    }
}

// Inicializar
document.addEventListener('DOMContentLoaded', () => {
    loadUsuarios();
});

// Obtener color del badge según el rol
function getRoleBadgeColor(role) {
    switch (role) {
        case 'ADMIN':
            return 'background: rgba(239, 68, 68, 0.1); color: #ef4444; border-color: #ef4444;';
        case 'USUARIO':
            return 'background: rgba(74, 158, 255, 0.1); color: var(--accent-blue); border-color: var(--accent-blue);';
        default:
            return '';
    }
}

// Renderizar tabla
function renderTable() {
    const tbody = document.getElementById('usuariosTable');
    
    if (usuarios.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                    No hay usuarios registrados
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = usuarios.map(usuario => {
        const isAdmin = usuario.usuarioRole === 'ADMIN';
        const adminCount = usuarios.filter(u => u.usuarioRole === 'ADMIN').length;
        const canDelete = !(isAdmin && adminCount === 1);
        
        return `
            <tr>
                <td style="font-weight: 600;">${usuario.id}</td>
                <td>
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <div class="avatar" style="background: rgba(245, 158, 11, 0.1); color: var(--accent-amber);">
                            ${isAdmin 
                                ? '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>'
                                : '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>'
                            }
                        </div>
                        <span style="font-weight: 500;">${usuario.nombre}</span>
                    </div>
                </td>
                <td>
                    <span style="font-family: monospace; font-size: 14px; color: var(--text-secondary);">@${usuario.username}</span>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 4px; font-size: 14px;">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="color: var(--text-secondary);">
                            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                            <polyline points="22,6 12,13 2,6"></polyline>
                        </svg>
                        ${usuario.email}
                    </div>
                </td>
                <td>
                    <span class="badge" style="${getRoleBadgeColor(usuario.usuarioRole)}">${usuario.usuarioRole}</span>
                </td>
                <td class="text-right">
                    <div class="action-buttons">
                        <button class="btn btn-ghost" onclick="editUsuario(${usuario.id})" title="Editar">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                                <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                            </svg>
                        </button>
                        <button class="btn btn-ghost btn-danger" onclick="deleteUsuario(${usuario.id})" title="Eliminar" ${!canDelete ? 'disabled style="opacity: 0.5; cursor: not-allowed;"' : ''}>
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
    document.getElementById('totalUsuarios').textContent = usuarios.length;
    
    const admins = usuarios.filter(u => u.usuarioRole === 'ADMIN').length;
    document.getElementById('totalAdmins').textContent = admins;
    
    const otros = usuarios.filter(u => u.usuarioRole !== 'ADMIN').length;
    document.getElementById('totalOtros').textContent = otros;
}

// Guardar en localStorage (ya no se usa, pero se mantiene por compatibilidad)
function saveUsuarios() {
    // Ya no se usa localStorage
}

// Abrir modal crear
function openCreateModal() {
    document.getElementById('createModal').classList.add('active');
    document.getElementById('createNombre').value = '';
    document.getElementById('createUserName').value = '';
    document.getElementById('createEmail').value = '';
    document.getElementById('createPassword').value = '';
    document.getElementById('createRole').value = '';
}

// Cerrar modal crear
function closeCreateModal() {
    document.getElementById('createModal').classList.remove('active');
}

// Crear usuario
async function createUsuario() {
    const nombre = document.getElementById('createNombre').value.trim();
    const username = document.getElementById('createUserName').value.trim();
    const email = document.getElementById('createEmail').value.trim();
    const password = document.getElementById('createPassword').value;
    const usuarioRole = document.getElementById('createRole').value;
    
    if (!nombre || !username || !email || !password || !usuarioRole) {
        alert('Por favor completa todos los campos');
        return;
    }
    
    try {
        const nuevoUsuario = {
            nombre,
            username,
            email,
            password,
            usuarioRole
        };
        
        await apiRequest('/usuario', 'POST', nuevoUsuario);
        await loadUsuarios();
        closeCreateModal();
    } catch (error) {
        console.error('Error al crear usuario:', error);
    }
}

// Editar usuario
function editUsuario(id) {
    const usuario = usuarios.find(u => u.id === id);
    if (!usuario) return;
    
    currentEditId = id;
    document.getElementById('editNombre').value = usuario.nombre;
    document.getElementById('editUserName').value = usuario.username;
    document.getElementById('editEmail').value = usuario.email;
    document.getElementById('editPassword').value = '';
    document.getElementById('editRole').value = usuario.usuarioRole;
    document.getElementById('editModal').classList.add('active');
}

// Cerrar modal editar
function closeEditModal() {
    document.getElementById('editModal').classList.remove('active');
    currentEditId = null;
}

// Actualizar usuario
async function updateUsuario() {
    if (!currentEditId) return;
    
    const nombre = document.getElementById('editNombre').value.trim();
    const username = document.getElementById('editUserName').value.trim();
    const email = document.getElementById('editEmail').value.trim();
    const password = document.getElementById('editPassword').value;
    const usuarioRole = document.getElementById('editRole').value;
    
    if (!nombre || !username || !email || !usuarioRole) {
        alert('Por favor completa todos los campos obligatorios');
        return;
    }
    
    try {
        const usuarioActualizado = {
            nombre,
            username,
            email,
            usuarioRole
        };
        
        // Solo incluir password si se proporcionó uno nuevo
        if (password != null && password.trim() !== "") {
            usuarioActualizado.password = password;
        }
        
        await apiRequest(`/usuario/${currentEditId}`, 'PUT', usuarioActualizado);
        await loadUsuarios();
        closeEditModal();
    } catch (error) {
        console.error('Error al actualizar usuario:', error);
    }
}

// Eliminar usuario
async function deleteUsuario(id) {
    const usuario = usuarios.find(u => u.id === id);
    if (!usuario) return;
    
    const adminCount = usuarios.filter(u => u.usuarioRole === 'ADMIN').length;
    if (usuario.usuarioRole === 'ADMIN' && adminCount === 1) {
        alert('No se puede eliminar el último administrador');
        return;
    }
    
    if (!confirm('¿Estás seguro de que deseas eliminar este usuario?')) return;
    
    try {
        await apiRequest(`/usuario/${id}`, 'DELETE');
        await loadUsuarios();
    } catch (error) {
        console.error('Error al eliminar usuario:', error);
    }
}

// Cerrar modales al hacer clic fuera
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

