$ ->
  $.get "/flights", (flights) ->
    $.each flights, (index, flight) ->
      source = $("<div>").addClass("source").text flight.source
      destination = $("<div>").addClass("destination").text flight.destination
      carrier = $("<div>").addClass("carrier").text flight.carrier
      dateOfTravel = $("<div>").addClass("dateOfTravel").text flight.dateOfTravel
      dateOfPriceFall = $("<div>").addClass("dateOfPriceFall").text flight.dateOfPriceFall
      predictedPrice = $("<div>").addClass("predictedPrice").text flight.predictedPrice
      latitude = $("<div>").addClass("latitude").text flight.latitude
      longitude = $("<div>").addClass("longitude").text flight.longitude
      distance = $("<div>").addClass("distance").text flight.distance
      seats = $("<div>").addClass("seats").text flight.seats



      $("#flights").append $("<li>").append(source).append(destination).append(carrier),append(dateOfTravel),append(dateOfPriceFall),append(predictedPrice),append(latitude),append(longitude), append(distance), append(seats)