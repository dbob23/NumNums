var map;

function initMap() {

    var options = {
        center: {lat: -34.397, lng: 150.644},
        zoom: 8
    };
    map = new google.maps.Map(document.getElementById('map'), options);

    var marker = new google.maps.Marker({
                position:{lat: 38.5950619, lng: -90.2291565},
                map:map
               });
            }
}
