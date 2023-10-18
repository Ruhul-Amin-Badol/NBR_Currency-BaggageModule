document.addEventListener("DOMContentLoaded", function () {  
    event.preventDefault();
    console.log("hi i am baggage");

    // Count All baggage 
    const totalShowApplicationId = document.getElementById("totalShowApplication").value;
  
    $.ajax({
      url: "http://localhost:8080/baggagestart/countAllBaggage", 
      type: "GET",
      success: function (data) {
        // Handle the AJAX response here
        console.log(data);
        document.getElementById("totalShowApplication").innerText=data;

       
       
        // ;
      },
      error: function (error) {
        // Handle AJAX errors here
        console.error(error);
      },
    });



    //---> Count approve baggage <--//
    const totalBaggageapproveapplicationId = document.getElementById("totalBaggageapproveapplication").value;
  
    $.ajax({
      url: "http://localhost:8080/baggagestart/countApprovedBaggage", 
      type: "GET",
      success: function (data) {
        console.log(data);
        document.getElementById("totalBaggageapproveapplication").innerText=data;

      },
      error: function (error) {
        console.error(error);
      },
    });

    //---> Count unapprove baggage <--//
    const totalBaggageunapproveapplicationId = document.getElementById("totalBaggageUnapprove").value;
  
    $.ajax({
      url: "http://localhost:8080/baggagestart/countunApprovedBaggage", 
      type: "GET",
      success: function (data) {
        console.log(data);
        document.getElementById("totalBaggageUnapprove").innerText=data;

      },
      error: function (error) {
        console.error(error);
      },
    });

        //---> Count RejectBaggageapplication <--//
        const totalRejectBaggageapplicationId = document.getElementById("totalRejectBaggage").value;
  
        $.ajax({
          url: "http://localhost:8080/baggagestart/countrejectedBaggage", 
          type: "GET",
          success: function (data) {
            console.log(data);
            document.getElementById("totalRejectBaggage").innerText=data;
    
          },
          error: function (error) {
            console.error(error);
          },
        });

         //---> Count currency uncheckedstatuscount  <--//
         const totaluncheckedstatuscountapplication = document.getElementById("uncheckedstatuscount").value;
  
         $.ajax({
           url: "http://localhost:8080/currencystart/uncheckedstatuscount", 
           type: "GET",
           success: function (data) {
             console.log(data);
             document.getElementById("uncheckedstatuscount").innerText=data;
     
           },
           error: function (error) {
             console.error(error);
           },
         });

            //---> Count checkedstatuscount <--//
            const totalcheckedstatuscountapplication = document.getElementById("Currencycheckedstatuscount").value;
  
            $.ajax({
              url: "http://localhost:8080/currencystart/checkedstatuscount", 
              type: "GET",
              success: function (data) {
                console.log(data);
                document.getElementById("Currencycheckedstatuscount").innerText=data;
        
              },
              error: function (error) {
                console.error(error);
              },
            });

                        //---> Count checkedstatuscount <--//
                        const totalAllcheckedstatuscountapplication = document.getElementById("Currencyallcheckedstatuscount").value;
  
                        $.ajax({
                          url: "http://localhost:8080/currencystart/allstatuscount", 
                          type: "GET",
                          success: function (data) {
                            console.log(data);
                            document.getElementById("Currencyallcheckedstatuscount").innerText=data;
                    
                          },
                          error: function (error) {
                            console.error(error);
                          },
                        });
   
      //---> Count checkedstatuscount <--//
      const totalrejectedstatuscountapplication = document.getElementById("rejectedCurrencystatuscount").value;
  
      $.ajax({
        url: "http://localhost:8080/currencystart/rejectedstatuscount", 
        type: "GET",
        success: function (data) {
          console.log(data);
          document.getElementById("rejectedCurrencystatuscount").innerText=data;
  
        },
        error: function (error) {
          console.error(error);
        },
      });


  

  

  });