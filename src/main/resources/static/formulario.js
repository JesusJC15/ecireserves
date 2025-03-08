document.getElementById("lab-form").addEventListener("submit", function(event) {
    event.preventDefault();

    let openingTime = document.getElementById("openingTime").value;
    let closingTime = document.getElementById("closingTime").value;

    let formData = {
        id: document.getElementById("id").value,
        name: document.getElementById("name").value,
        capacity: parseInt(document.getElementById("capacity").value),
        computers: parseInt(document.getElementById("computers").value),
        description: document.getElementById("description").value.trim(),
        openingTime: new Date(openingTime).toISOString(), // Convertir a formato ISO 8601
        closingTime: new Date(closingTime).toISOString(),
        day: document.getElementById("day").value
    };

    console.log("Datos enviados:", formData);

    fetch("/api/laboratory", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw new Error(err.message || "Error desconocido"); });
        }
        return response.json();
    })
    .then(data => {
        alert("Laboratorio registrado correctamente");
        document.getElementById("lab-form").reset();
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Error al registrar el laboratorio: " + error.message);
    });
});
