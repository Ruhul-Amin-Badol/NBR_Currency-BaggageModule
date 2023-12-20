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
    //alert("repopulatePaymentHistory")
    let baggage_id = $("#baggage_id").val();
    $.ajax({
        url: "/baggageshow/get-payment-history",
        type: "GET",
        data: { baggage_id },
        success: function(response) {

           populatePaymentHistoryTable(response.paymentHistoryList,response.totalPaidAmount,response.totalTaxAmount);
          // console.log("testing");
          //("repopulatePaymentHistory");
          totalPaidAmount =  parseFloat(response.totalPaidAmount).toFixed(2);
          totalTaxAmount = parseFloat(response.totalTaxAmount).toFixed(2);
          console.log(totalPaidAmount+"  "+totalTaxAmount)
          const totalPayableAmount = totalTaxAmount - totalPaidAmount;
          const approveButton = $("#toggleButton");
          console.log("totalTaxAmount repopulatePaymentHistory==================="+totalTaxAmount);
          console.log("totalPaidAmount repopulatePaymentHistory==================="+totalPaidAmount);
          console.log("totalPayableAmount repopulatePaymentHistory==================="+totalPayableAmount);
           if (totalPayableAmount == 0){
            approveButton.prop('disabled', false); // Enable the button
          //  console.log("disavle"+totalPayableAmount);
        } else {
            approveButton.prop('disabled', true); // Disable the button
           // console.log("anable"+totalPayableAmount);
        }
        },
        error: function(error) {
            console.error(error);
        }
    });
}
$(document).ready(function() {
    // When the document is ready, make an AJAX call to fetch payment history for baggage_id = 1
    let baggage_id = $("#baggage_id").val(); 
   //alert(baggage_id)
    $.ajax({
        url: "/baggageshow/get-payment-history", // URL to your backend endpoint
        type: "GET",
        data: { baggage_id: baggage_id }, // Pass baggage_id as a parameter to fetch the relevant data
        success: function(response) {
            // On success, populate the table with the fetched data
            //populatePaymentHistoryTable(response.paymentHistoryList);
            populatePaymentHistoryTable(response.paymentHistoryList,response.totalPaidAmount,response.totalTaxAmount);
           // alert("totalPayableAmount")
           totalPaidAmount =  parseFloat(response.totalPaidAmount).toFixed(2);
           totalTaxAmount = parseFloat(response.totalTaxAmount).toFixed(2);
            console.log(totalPaidAmount+"  "+totalTaxAmount);
           const totalPayableAmount = totalTaxAmount - totalPaidAmount;
          //  alert(totalPayableAmount)
            const approveButton = $("#toggleButton");
            const totalPayableAmountFormatted = totalPayableAmount.toFixed(2);
            console.log(totalPayableAmountFormatted+"  "+totalPayableAmount);
          
         // const approveButton = $("#toggleButton");
         console.log("totalTaxAmount==================="+totalTaxAmount);
         console.log("totalPaidAmount==================="+totalPaidAmount);
         console.log("totalPayableAmount==================="+totalPayableAmount);
         console.log("totalPayableAmountFormatted======================"+totalPayableAmountFormatted)
           if (totalPayableAmountFormatted ==0 ){
            approveButton.prop('disabled',false ); // Enable the button
          //  console.log("disavle"+totalPayableAmount);
        } else {
            approveButton.prop('disabled', true); // Disable the button
           // console.log("anable"+totalPayableAmount);
        }
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
        newRow.append(`<td>${item.transaction_id}</td>`);
        if (item.paid_amount>=0){

        newRow.append(`<td>${item.paid_amount}</td>`);
        }else{
            let absolutePayableAmount = Math.abs(item.paid_amount);
            newRow.append(`<td>${absolutePayableAmount} <span  style="float: right;font-weight: bolder; color: orangered;">Refund</span></td>`);
        }
      //  newRow.append(`<td><button onclick="performAction(${item.id})">Action</button></td>`);
        tableBody.append(newRow);
      //  tableBody.append("================================");


    });
    //alert(item.payment_id[0])
    // Add the total payment amount as the last row
    const totalRow = $("<tr>");
  

    totalPaidAmount =  parseFloat(totalPaidAmount).toFixed(2);
    totalTaxAmount = parseFloat(totalTaxAmount).toFixed(2);
     //console.log(totalPaidAmount+"  "+totalTaxAmount);
    const totalPayableAmount = totalTaxAmount - totalPaidAmount;

    totalRow.append(`<td colspan="1" class="fw-bold">Total Tax Amount : ${totalTaxAmount}</td>`); // colspan to cover the entire row
    totalRow.append(`<td class="fw-bold">Total Paid Amount  : ${totalPaidAmount}</td>`);
    payableAmount = totalPayableAmount.toFixed(2)
    //totalRow.append(`<td colspan="2">Total Payable Amount : ${payableAmount}</td>`);
    //alert(totalPayableAmount)
    const refundAmountCell = $("#refundAmountCell");
    if (payableAmount>=0){

        totalRow.append(`<td colspan="2" class="fw-bold">Total Payable Amount : ${payableAmount}</td>`);
    }else{
        let absolutePayableAmount = Math.abs(payableAmount);

        totalRow.append(`<td colspan="2"> Refund Amount : ${absolutePayableAmount}
        <input type="hidden" id="payableAmount_id" name="payableAmount" value="${payableAmount}" />
      
        </td>` );
        
    }
    // <button style="float: right;" id="refundAmountBtnId" type="button" class="btn btn-warning" onclick="adjustRefund()">Adjust Refund ( ${absolutePayableAmount })</button>
    tableBody.append(totalRow);
    //totalRow.append(`<td onclick="adjustRefund()"><button>ddd</button></td>`);

}

function adjustRefund() {
    let baggage_id = $("#baggage_id").val();
    let paymentId = $("#payment_id").val();
    let payableAmountId = $("#payableAmount_id").val();

    const data = {
        baggage_id: baggage_id,
        payableAmount: payableAmountId,
        payment_id: paymentId
    };

    $.ajax({
        url: "/baggageshow/update-refund-amount",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            $("#payment_date").val("");
            console.log(response);

            if (response && response.success === true) {
                const refundAmountCell = $("#refundAmountCell");
                const totalRow = $("#paymentHistoryAjaxtable tbody tr:last");

                if (payableAmountId >= 0) {
                    totalRow.find('td:eq(2)').html(`Total Payable Amount : ${payableAmountId}`);
                    refundAmountCell.empty(); // Clear the refund cell if applicable
                } else {
                    let absolutePayableAmount = Math.abs(payableAmountId);
                    totalRow.find('td:eq(2)').html(`Refund Amount: ${absolutePayableAmount}`);
                    totalRow.find('td:eq(2)').append(`<button style="float: right;" id="refundAmountBtnId" type="button" class="btn btn-warning" onclick="adjustRefund(${payableAmountId})">Adjust Refund (${absolutePayableAmount})</button>`);
                }

                // Repopulate the payment history table after the refund adjustment
                repopulatePaymentHistory();
            }

            // Check payable amount for enabling/disabling the approve button
            totalPaidAmount = parseFloat(response.totalPaidAmount).toFixed(2);
            totalTaxAmount = parseFloat(response.totalTaxAmount).toFixed(2);
            const totalPayableAmount = totalTaxAmount - totalPaidAmount;
            const approveButton = $("#toggleButton");

            if (totalPayableAmount == 0) {
                approveButton.prop('disabled', false); // Enable the button
            } else {
                approveButton.prop('disabled', true); // Disable the button
            }

   // Create a new row to display the success message

   const successSpan = $('<p class="success-message text-center text-success">Refund Successfully</p>');

   // Append the success message span to the payment history table
   $('#paymentHistoryAjaxtable').append(successSpan);

   setTimeout(function () {
       // Fade out and remove the success message span after 5 seconds
       successSpan.fadeOut('slow', function () {
           $(this).remove();
       });
   }, 8000);
        },
        error: function (error) {
            // Handle error
            console.error(error);
        }
    });
}


