document.addEventListener("DOMContentLoaded", function () {  
    event.preventDefault();
    console.log("hi i am baggage");

    // Count All baggage 
   // const totalShowApplicationId = document.getElementById("totalShowApplication").value;
  
    $.ajax({
      url: "/baggagestart/countAllBaggage", 
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
   // const totalBaggageapproveapplicationId = document.getElementById("totalBaggageapproveapplication").value;
  
    $.ajax({
      url: "/baggagestart/countApprovedBaggage", 
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
    //const totalBaggageunapproveapplicationId = document.getElementById("totalBaggageUnapprove").value;
  
    $.ajax({
      url: "/baggagestart/countunApprovedBaggage", 
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
        //const totalRejectBaggageapplicationId = document.getElementById("totalRejectBaggage").value;
  
        $.ajax({
          url: "/baggagestart/countrejectedBaggage", 
          type: "GET",
          success: function (data) {
            console.log(data);
            document.getElementById("totalRejectBaggage").innerText=data;
    
          },
          error: function (error) {
            console.error(error);
          },
        });
       // console.log("====================================================");
         //---> Count currency uncheckedstatuscount  <--//
        // const totaluncheckedstatuscountapplication = document.getElementById("currencyUnapproveCountId").value;
  
         $.ajax({
           url: "/currencystart/uncheckedstatuscount", 
           type: "GET",
           success: function (data) {
             console.log(data);
             document.getElementById("currencyUnapproveCountId").innerText=data;
     
           },
           error: function (error) {
             console.error(error);
           },
         });

            //---> Count checkedstatuscount <--//
           // const totalcheckedstatuscountapplication = document.getElementById("currencyApproveCountId").value;
  
            $.ajax({
              url: "/currencystart/checkedstatuscount", 
              type: "GET",
              success: function (data) {
                console.log(data);
                document.getElementById("currencyApproveCountId").innerText=data;
        
              },
              error: function (error) {
                console.error(error);
              },
            });

                        //---> Count checkedstatuscount <--//
                      //  const totalAllcheckedstatuscountapplication = document.getElementById("currencyAllCountId").value;
  
                        $.ajax({
                          url: "/currencystart/allstatuscount", 
                          type: "GET",
                          success: function (data) {
                            console.log(data);
                            document.getElementById("currencyAllCountId").innerText=data;
                    
                          },
                          error: function (error) {
                            console.error(error);
                          },
                        });
   
      //---> Count checkedstatuscount <--//
     // const totalrejectedstatuscountapplication = document.getElementById("rejectedCurrencystatuscount").value;
  
      $.ajax({
        url: "/currencystart/rejectedstatuscount", 
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