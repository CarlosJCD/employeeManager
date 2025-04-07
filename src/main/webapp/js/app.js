// 📄 js/app.js (actualizado con consumo SOAP)

const apiBase = "/employeeManager/api"; // REST base path
const soapURL = "/employeeManager/EmpleadoSOAPService"; // SOAP endpoint

async function cargarEmpleados() {
  const res = await fetch(`${apiBase}/empleados`);
  const empleados = await res.json();

  const lista = document.getElementById("empleados-list");
  const select = document.getElementById("empleado");

  lista.innerHTML = "";
  select.innerHTML = "";

  empleados.forEach(e => {
    const li = document.createElement("li");
    li.textContent = `${e.nombre} - ${e.puesto}`;
    lista.appendChild(li);

    const option = document.createElement("option");
    option.value = e.id;
    option.textContent = e.nombre;
    select.appendChild(option);
  });

  if (empleados.length > 0) {
    cargarTareas(empleados[0].id);
    select.addEventListener("change", () => {
      cargarTareas(select.value);
    });
  }
}

document.getElementById("tarea-form").addEventListener("submit", async e => {
  e.preventDefault();
  const id = document.getElementById("empleado").value;
  const desc = document.getElementById("desc").value;

  await fetch(`${apiBase}/tareas/asignar`, {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: `empleadoId=${id}&descripcion=${encodeURIComponent(desc)}`
  });

  alert("Tarea asignada");
  cargarTareas(id);
  document.getElementById("desc").value = "";
});

async function cargarTareas(id) {
  const res = await fetch(`${apiBase}/tareas/pendientes/${id}`);
  const tareas = await res.json();

  const lista = document.getElementById("tareas-list");
  lista.innerHTML = "";
  tareas.forEach(t => {
    const li = document.createElement("li");
    li.textContent = `${t.descripcion} ${t.completada ? "(✔)" : "(pendiente)"}`;
    lista.appendChild(li);
  });
}

document.getElementById("empleado-form").addEventListener("submit", async e => {
  e.preventDefault();

  const nombre = document.getElementById("nuevo-nombre").value;
  const puesto = document.getElementById("nuevo-puesto").value;

  const soapEnvelope = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:web="http://soap.service.example.com/">
      <soapenv:Header/>
      <soapenv:Body>
        <web:registrarEmpleado>
          <nombre>${nombre}</nombre>
          <puesto>${puesto}</puesto>
        </web:registrarEmpleado>
      </soapenv:Body>
    </soapenv:Envelope>`;

  const res = await fetch(soapURL, {
    method: "POST",
    headers: {
      "Content-Type": "text/xml; charset=utf-8",
      "SOAPAction": "" // Puedes omitirlo o usar "registrarEmpleado" si es requerido por tu servidor
    },
    body: soapEnvelope
  });

  if (res.ok) {
    alert("Empleado registrado vía SOAP");
    cargarEmpleados();
    document.getElementById("nuevo-nombre").value = "";
    document.getElementById("nuevo-puesto").value = "";
  } else {
    alert("Error al registrar SOAP");
    console.error(await res.text());
  }
});

window.onload = cargarEmpleados;