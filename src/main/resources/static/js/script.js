// Datos de los módulos
const modules = [
    {
        title: "Domicilios",
        description: "Gestiona las direcciones de los pacientes",
        icon: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
            <circle cx="12" cy="10" r="3"></circle>
        </svg>`,
        color: "#4a9eff"
    },
    {
        title: "Odontólogos",
        description: "Administra el equipo de profesionales",
        icon: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
        </svg>`,
        color: "#10b981"
    },
    {
        title: "Pacientes",
        description: "Gestiona la información de pacientes",
        icon: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
        </svg>`,
        color: "#06b6d4"
    },
    {
        title: "Turnos",
        description: "Administra las citas y turnos",
        icon: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="16" y1="2" x2="16" y2="6"></line>
            <line x1="8" y1="2" x2="8" y2="6"></line>
            <line x1="3" y1="10" x2="21" y2="10"></line>
        </svg>`,
        color: "#8b5cf6"
    },
    {
        title: "Usuarios",
        description: "Gestiona usuarios del sistema",
        icon: `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
        </svg>`,
        color: "#f59e0b"
    }
];

// Función para crear las tarjetas de módulos
function createModuleCards() {
    const modulesGrid = document.getElementById('modulesGrid');
    
    modules.forEach(module => {
        const card = document.createElement('a');
        card.href = '#';
        card.className = 'module-card';
        
        card.innerHTML = `
            <div class="module-icon" style="color: ${module.color}">
                ${module.icon}
            </div>
            <div class="module-title">${module.title}</div>
            <div class="module-description">${module.description}</div>
            <button class="module-button">
                Acceder →
            </button>
        `;
        
        // Configurar el href según el módulo
        const hrefMap = {
            'Domicilios': 'domicilios.html',
            'Odontólogos': 'odontologos.html',
            'Pacientes': 'pacientes.html',
            'Turnos': 'turnos.html',
            'Usuarios': 'usuarios.html'
        };
        
        card.href = hrefMap[module.title] || '#';
        
        modulesGrid.appendChild(card);
    });
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {
    createModuleCards();
});

