$("#addTOPaymenyHistory").click(function () {
    const payment_id = $("#payment_id").val();
    const payment_date = $("#payment_date").val();
    const calan_no = $("#calan_no").val();
    const paid_amount = $("#paid_amount").val();
    const baggage_id = $("#baggage_id").val();
    console.log(baggage_id, payment_id, payment_date, calan_no, paid_amount);

    const data = {
        baggage_id,
        paid_amount,
        payment_id,
        payment_date,
        calan_no,
    };

    if (paid_amount !== "" && calan_no !== "") {
        $.ajax({
            url: "/baggageshow/confirm-pay-by-admin",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                if (response.currencyAmount !== "" && response.numberOfNote !== "") {
                    addcurrencyData.push(response);

                    // Call the function to repopulate the payment history table
                    repopulatePaymentHistory();
                    
                    $("#payment_date").val("");
                    $("#calan_no").val("");
                    $("#paid_amount").val("");
                } else {
                    alert("Note Type, Number of Note, Amount is required");
                }
            },
            error: function (error) {
                console.error(error);
            },
        });
    } else {
        alert("Insert required fields");
    }
});

// Function to fetch and repopulate payment history
function repopulatePaymentHistory() {
    let baggage_id = $("#baggage_id").val();
    $.ajax({
        url: "/baggageshow/get-payment-history",
        type: "GET",
        data: { baggage_id },
        success: function(response) {
           // populatePaymentHistoryTable(response);
           //populatePaymentHistoryTable(response.paymentHistoryList);
           //displayTotalPaidAmount(response.totalPaidAmount);
           populatePaymentHistoryTable(response.paymentHistoryList,response.totalPaidAmount,response.totalTaxAmount);


            
        },
        error: function(error) {
            console.error(error);
        }
    });
}
$(document).ready(function() {
    // When the document is ready, make an AJAX call to fetch payment history for baggage_id = 1
    let baggage_id = $("#baggage_id").val(); 
    $.ajax({
        url: "/baggageshow/get-payment-history", // URL to your backend endpoint
        type: "GET",
        data: { baggage_id: baggage_id }, // Pass baggage_id as a parameter to fetch the relevant data
        success: function(response) {
            // On success, populate the table with the fetched data
            //populatePaymentHistoryTable(response.paymentHistoryList);
            populatePaymentHistoryTable(response.paymentHistoryList,response.totalPaidAmount,response.totalTaxAmount);
           // displayTotalPaidAmount(response.totalPaidAmount);
        },
        error: function(error) {
            // Handle errors here
            console.error(error);
        }
    });


    // Function to perform an action on a specific payment history item
    function performAction(paymentId) {
        // Your logic to perform an action on a specific payment history item
        // For example, editing or deleting the payment record
    }
});

function populatePaymentHistoryTable(data,totalPaidAmount,totalTaxAmount) {
    // Assuming 'data' is an array of objects containing payment history information
    const tableBody = $("#paymentHistoryAjaxtable tbody");
    tableBody.empty(); // Clear the table body before populating

    data.forEach(function(item) {
        const newRow = $("<tr>");
        newRow.append(`<td>${item.payment_id}</td>`);
        // const date = new Date(item.payment_date);


        // const formattedDate = date.toISOString().split('T')[0]; // Extracting date (YYYY-MM-DD)

        newRow.append(`<td>${item.payment_date}</td>`);
        newRow.append(`<td>${item.calan_no}</td>`);
        newRow.append(`<td>${item.paid_amount}</td>`);
      //  newRow.append(`<td><button onclick="performAction(${item.id})">Action</button></td>`);
        tableBody.append(newRow);
      //  tableBody.append("================================");


    });
    // Add the total payment amount as the last row
    const totalRow = $("<tr>");

    totalRow.append(`<td colspan="4">Total Text Amount: ${totalTaxAmount} ======== Total Paid Amount:${totalPaidAmount} ===== Total Payable Amount: ${totalPaidAmount-totalTaxAmount}</td>`); // colspan to cover the entire row
   // totalRow.append(`<td>${totalTaxAmount}</td>`);


    //totalRow.append('<td>Total Paid Amount</td>'); // colspan to cover the entire row
    //totalRow.append(`<td>${totalPaidAmount}</td>`);



    //totalRow.append('<td>Total Payable Amount</td>'); // colspan to cover the entire row
   // totalRow.append(`<td>${totalPaidAmount-totalTaxAmount}</td>`);
   // alert(totalPaidAmount)
    tableBody.append(totalRow);

}