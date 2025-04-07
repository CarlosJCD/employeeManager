const SOAP_ENDPOINT = "http://localhost:8080/employee-manager/EmpleadoService"

const REST_ENDPOINT = "http://localhost:8080/employee-manager/api/empleados/"


const empleadoId = new URLSearchParams(window.location.search).get("id");
if (!empleadoId) { window.location.href = `index.html` }

console.log(document.getElementById("returnButton"));

document.getElementById("returnButton").addEventListener("click", ()=>{ window.location.href = `info-empleado.html?id=${empleadoId}` })


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

        document.getElementById("nombre").value = nombre ?? "";
        document.getElementById("puesto").value = puesto ?? "";
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

document.getElementById("editar-form").addEventListener("submit", async (e)=>{
    e.preventDefault();
    const empleado = {
        "id": empleadoId,
        "nombre": document.getElementById("nombre").value,
        "puesto": document.getElementById("puesto").value
    }

    const response = await fetch(`${REST_ENDPOINT+empleadoId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado)
    })

    const empleadoActualizado = await response.json();

    alert(`Usuario Actualizado con éxito: 
            Nombre: ${empleadoActualizado.nombre}       
            Puesto: ${empleadoActualizado.puesto}       
    `)
    
    window.location.href = `info-empleado.html?id=${empleadoId}`
    
    
})


cargarEmpleado()