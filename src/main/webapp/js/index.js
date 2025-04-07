const REST_ENDPOINT = "http://localhost:8080/employee-manager/api/"

async function cargarEmpleados() {
    try {
      const res = await fetch(`${REST_ENDPOINT}empleados`);
      const empleados = await res.json();

      const container = document.getElementById("empleados-container");
      empleados.forEach(emp => {
        const card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `
          <h3>${emp.nombre}</h3>
          <p><strong>ID:</strong> ${emp.id}</p>
          <p><strong>Puesto:</strong> ${emp.puesto}</p>
          <a href="info-empleado.html?id=${emp.id}">Detalles</a>
        `;
        container.appendChild(card);
      });
    } catch (err) {
      alert("Error cargando empleados: " + err);
    }
  }

  cargarEmpleados();