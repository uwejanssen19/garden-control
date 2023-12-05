// Wrap everything in a function (no global variable pollution)
// variable "input" contains data passed by openHAB
(function(inputData) {
    // on read: the polled number as string
    // on write: openHAB command as string
    var DIVIDE_BY = 10;
    var retVal = 100;
    // ensure SOC (= state of charge) can at most be 100%
    retVal = parseFloat(inputData) / DIVIDE_BY;
    if (retVal <= 100) 
        return retVal;
     
})(input)
