const SOAP_ENDPOINT = "http://localhost:8080/employee-manager/EmpleadoService"

console.log(document.getElementById('formEmpleado'))
document.getElementById('formEmpleado').addEventListener('submit', async (e) => {
    e.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const puesto = document.getElementById('puesto').value;
    
    const crearEmpleadoSOAPRequest = construirCrearEmpleadoSOAPRequest(nombre, puesto);
    
    try {
        const response = await fetch(SOAP_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'text/xml;charset=UTF-8',
            },
            body: crearEmpleadoSOAPRequest
        });
        console.log(crearEmpleadoSOAPRequest)
        
        alert("Submit")
        const xmlResponse = await response.text();
        
        console.log(xmlResponse);
        
        // Parse the XML response
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlResponse, 'application/xml');

        // Extract the employee data from the response
        const empleado = xmlDoc.getElementsByTagName('empleado')[0];
        const id = empleado.getElementsByTagName('id')[0]?.textContent;
        const nombre = empleado.getElementsByTagName('nombre')[0]?.textContent;
        const puesto = empleado.getElementsByTagName('puesto')[0]?.textContent;


        
        console.log('Empleado registrado:', { id, nombre, puesto });

        alert(`Empleado registrado: ${nombre} - ${puesto}`);

        window.location.href = "index.html"


    } catch (error) {
        console.log(error)
        alert("Error al registrar empleado: " + error);
    }

    function construirCrearEmpleadoSOAPRequest(nombre, puesto){
        return `<?xml version="1.0" encoding="UTF-8"?>
                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://soap.example.com/">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <web:registrarEmpleado>
                                <nombre>${nombre}</nombre>
                                <puesto>${puesto}</puesto>
                            </web:registrarEmpleado>
                        </soapenv:Body>
                    </soapenv:Envelope>`
    }

})


