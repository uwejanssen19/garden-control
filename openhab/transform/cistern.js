// 1. füllhöhe aus messhöhe = 780 - messhöhe
// 2. füllhöhe in prozent berechnen
// 3. Werte ausserhalb 0-100% ignorieren
(function(i) {
    // messfehler ignorieren
    var max = 780;
    var min = 80;
    var fill = max - i;
    // 700 => 100 % 
    // 0 => 0% 
    var retVal = 0;
    retVal = fill *(100/(max-min));
    if (retVal > 100) retVal = 100;
    if (retVal < 0) retVal = 0;
    return (Math.round(retVal * 100) / 100).toFixed(2);
})(input)