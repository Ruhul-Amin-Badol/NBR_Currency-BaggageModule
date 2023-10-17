document.addEventListener("DOMContentLoaded", function () {  
    event.preventDefault();
    console.log("hi i am baggage");


    //---> valueStay controller id pass <--//
    // Get the value of the 'generatedId' parameter
    const totalBaggageapproveapplicationId = document.getElementById("totalBaggageapproveapplication").value;
  
    $.ajax({
      url: "http://localhost:8080/baggagestart/countAllBaggage", 
      type: "GET",
      success: function (data) {
        // Handle the AJAX response here
        console.log(data);
        document.getElementById("totalBaggageapproveapplication").innerText=data;

       
       
        // ;
      },
      error: function (error) {
        // Handle AJAX errors here
        console.error(error);
      },
    });


  
   

  
  

  

  

  });