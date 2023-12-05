// Wrap everything in a function (no global variable pollution)
// variable "input" contains data passed by openHAB
(function(inputData) {
    // on read: the polled number as string
    // on write: openHAB command as string
    var DIVIDE_BY = 10;
    var retVal = 0;
    retVal = parseFloat(inputData) / DIVIDE_BY;
    return retVal;

})(input)
