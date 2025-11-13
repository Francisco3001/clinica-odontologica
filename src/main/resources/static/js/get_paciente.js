window.addEventListener('load', function () {
  const url = '/paciente/admin';

  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error('Network response was not ok: ' + response.status);
      return response.json();
    })
    .then(data => {
      // Asegurarnos de trabajar sobre el tbody
      const tableBody = document.getElementById('pacienteTableBody');
      tableBody.innerHTML = ''; // limpiar contenido previo

      // Si el endpoint devuelve un objeto y no un array, manejarlo:
      const pacientes = Array.isArray(data) ? data : (data ? [data] : []);

      pacientes.forEach(paciente => {
        const tr = document.createElement('tr');
        tr.id = 'tr_' + (paciente.id ?? '');

        const deleteButton = `<button id="btn_delete_${paciente.id}" type="button" onclick="deleteBy(${paciente.id})" class="btn btn-danger btn_delete">&times;</button>`;
        const updateButton = `<button id="btn_id_${paciente.id}" type="button" onclick="findBy(${paciente.id})" class="btn btn-info btn_id">${paciente.id}</button>`;

        // Usar nombres de atributos correctos (según tu modelo: numeroContacto, fechaIngreso)
        tr.innerHTML = `
          <td>${paciente.id ?? ''}</td>
          <td class="td_nombre">${(paciente.nombre ?? '').toString().toUpperCase()}</td>
          <td class="td_apellido">${(paciente.apellido ?? '').toString().toUpperCase()}</td>
          <td class="td_cedula">${paciente.numeroContacto ?? ''}</td>
          <td class="td_fechaDeIngreso">${paciente.fechaIngreso ?? ''}</td>
          <td class="td_email">${(paciente.email ?? '').toString().toUpperCase()}</td>
          <td>${updateButton}</td>
          <td>${deleteButton}</td>
        `;
        tableBody.appendChild(tr);
      });
    })
    .catch(error => {
      console.error('Error cargando pacientes:', error);
      // Aquí puedes mostrar un alert o mensaje en la UI
    });
});