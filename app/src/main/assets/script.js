const apiKey = 'PlSlQvEtRalFmpnKIBe6AZMjJabdZJMdlDzlbEQrw6U'; // Replace with your actual API key
const platform = new H.service.Platform({ apikey: apiKey });

const defaultLayers = platform.createDefaultLayers();

// Initialize the map, default center is Berlin
const map = new H.Map(
    document.getElementById('mapContainer'),
    defaultLayers.vector.normal.map,
    {
        zoom: 10,
        center: { lat: 52.5200, lng: 13.4050 } // Default center: Berlin
    }
);

const mapEvents = new H.mapevents.MapEvents(map);
const behavior = new H.mapevents.Behavior(mapEvents);
const ui = H.ui.UI.createDefault(map, defaultLayers);

// Marker for the user's location
let userMarker = null;

// Function to add a marker at a given location
function addMarker(lat, lng, title) {
    const marker = new H.map.Marker({ lat, lng });
    map.addObject(marker);
    if (title) {
        marker.setData(title);
    }
    return marker;
}

// Function to get and display the user's current location
function getCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            alert(`Your location: Latitude: ${latitude}, Longitude: ${longitude}`);  // Corrected string interpolation

            // Center the map on the user's location and add a marker
            map.setCenter({ lat: latitude, lng: longitude });
            map.setZoom(14);

            // Remove previous marker, if any
            if (userMarker) {
                map.removeObject(userMarker);
            }

            // Add new marker at the user's location
            userMarker = addMarker(latitude, longitude, 'Your Location');

            // Update the source field with current location
            document.getElementById('source').value = `Latitude: ${latitude}, Longitude: ${longitude}`;  // Corrected string interpolation
        }, (error) => {
            alert('Geolocation error: ' + error.message);
        });
    } else {
        alert('Geolocation is not supported by this browser.');
    }
}

// Event listener for the "Locate Me" button
document.getElementById('locateButton').addEventListener('click', getCurrentLocation);

// Call this function on page load to get the user's location automatically
getCurrentLocation();
