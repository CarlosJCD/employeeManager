const SOAP_ENDPOINT = "http://localhost:8080/employee-manager/EmpleadoService"
const REST_ENDPOINT = "http://localhost:8080/employee-manager/api/tareas/"

const empleadoId = new URLSearchParams(window.location.search).get("id");
if (!empleadoId) { window.location.href = "index.html" }

async function cargarEmpleado() {
    try {
        const res = await fetch( SOAP_ENDPOINT, {
                method: "POST",
                headers: {
                    "Content-Type": "text/xml;charset=UTF-8",
                },
                body: construirCargarEmpleadoSOAPRequest(empleadoId)
            }
        );
        const responseText = await res.text();

        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(responseText, "text/xml");

        // Extraer los datos del empleado
        const id = xmlDoc.getElementsByTagName("id")[0]?.textContent;
        const nombre = xmlDoc.getElementsByTagName("nombre")[0]?.textContent;
        const puesto = xmlDoc.getElementsByTagName("puesto")[0]?.textContent;

        console.log("Empleado:");
        console.log("ID:", id);
        console.log("Nombre:", nombre);
        console.log("Puesto:", puesto);

        document.getElementById("nombre").textContent = nombre ?? "N/A";
        document.getElementById("puesto").textContent = puesto ?? "N/A";
        document.getElementById("idEmpleado").textContent = id ?? "N/A";
    } catch (error) {
        console.log(error);
    }
    
}

function construirCargarEmpleadoSOAPRequest(idEmpleado){
    return `<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://soap.example.com/">
    <soapenv:Header/>
    <soapenv:Body>
        <web:obtenerEmpleado>
            <id>${idEmpleado}</id>
        </web:obtenerEmpleado>
    </ soapenv:Body>
</soapenv:Envelope>`
}

async function cargarTareas() {
  const res = await fetch(REST_ENDPOINT + empleadoId);
  const tareas = await res.json();
  const contenedor = document.getElementById("listaTareas");
  contenedor.innerHTML = "";

  tareas.forEach(tarea => {
    const div = document.createElement("div");
    div.className = "card";
    div.innerHTML = `
      <h3>${tarea.descripcion}</h3>
      <p>Completada: ${tarea.completada ? "Sí" : "No"}</p>
      ${!tarea.completada
        ? `<button class="button complete" onclick="completarTarea(${tarea.id})">Completar</button>`
        : ""}
    `;
    contenedor.appendChild(div);
  });
}

function mostrarFormularioTarea() {
  document.getElementById("formTarea").style.display = "block";
}

async function asignarTarea() {
  const desc = document.getElementById("descripcion").value;
  
  const tarea = { "descripcion": desc, "completada": false, "empleado": { "id": empleadoId }}

  console.log(tarea)
  
  await fetch(`${REST_ENDPOINT}asignar`, {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
      },
    body: JSON.stringify(tarea)
  });

  document.getElementById("descripcion").value = "";
  document.getElementById("formTarea").style.display = "none";
  cargarTareas();
}

async function completarTarea(idTarea) {
  await fetch(`http://localhost:8080/empleado-app/api/tareas/completar/${idTarea}`, {
    method: "POST"
  });
  cargarTareas();
}

// Inicializar
cargarEmpleado();
cargarTareas();