function fetchWeather() {
    const city = document.getElementById("city").value;
    if (!city) {
        alert("Please enter a city name!");
        return;
    }

    fetch(`http://localhost:9090/weather?city=${city}`)
        .then(response => response.json())
        .then(data => {
            if (data.cod && data.cod !== 200) {
                document.getElementById("weatherResult").innerHTML = `<p style="color: red;">${data.message}</p>`;
                return;
            }

            document.getElementById("weatherResult").innerHTML = `
                <h2>${data.name}, ${data.sys.country}</h2>
                <p>🌡 Temperature: ${data.main.temp}°C</p>
                <p>💨 Wind Speed: ${data.wind.speed} m/s</p>
                <p>📌 Weather: ${data.weather[0].description}</p>
                <img src="https://openweathermap.org/img/w/${data.weather[0].icon}.png" alt="Weather Icon">
            `;
        })
        .catch(error => {
            console.error("Error fetching weather data:", error);
            document.getElementById("weatherResult").innerHTML = `<p style="color: red;">Failed to fetch data. Try again later!</p>`;
        });
}
