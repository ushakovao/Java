/**
 *
 * @author Ioannis Svigkos 2008
 */
// This class corresponds to binomial tree option pricing.
// Methods that calculate equity movement first are provided.
// Then, methods that calculate option price in each note are
// presented. Note that there is also implementation about
// American options, however they need further refinement and testing
import java.text.DecimalFormat;
public class BinomialTree {
    // Private field members
    private DecimalFormat _decimalFormat;
    private double[][] _equityMovement;
    // stores values of equity for each period (upper & lower)
    private double[][] _optionPriceMovement;
    // stores price of call option for each period (upper & lower)
    private double[][] _americanPutOptionPriceMovement; // stores price of put option for each period

    private double _assetPrice;
    private double _strikePrice;
    private double _riskFreeRate;
    private double _sigma;
    private double _timeToMaturity;
    private double _iteration;

    private double _upperFactor;
    private double _lowerFactor;
    private double _probability;
    private double _deltaT;
    // Constructor
    public BinomialTree (
            double _assetPrice, double _strikePrice, double _riskFreeRate,
            double _sigma, double _timeToMaturity,
            double _iteration) {
        // Set decimal Format
        _decimalFormat = new DecimalFormat("#.##");
        // Initialise private fields
        this._assetPrice = _assetPrice;
        this._strikePrice = _strikePrice;
        this._riskFreeRate = _riskFreeRate;
        this._sigma = _sigma;
        this._timeToMaturity = _timeToMaturity;
        this._iteration = _iteration;
        // Initilialise further parameters
        this._deltaT = calculateDeltaT();
        this._upperFactor = calculateUpperFactor();
        this._lowerFactor = calculateLowerFactor();
        this._probability = calculateProbability();
    }
    // calculate delta time
    public double calculateDeltaT() {
        return _timeToMaturity/_iteration;
    }
    //calculate upper factor based on parameters given
    public double calculateUpperFactor() {
        return Math.pow(Math.E, _sigma * Math.sqrt(_deltaT));
    }
    //calculate lower factor based on parameters given
    public double calculateLowerFactor() {
        return Math.pow(Math.
                E, (-_sigma) * Math.sqrt(_deltaT));
    }
// calculate probability based on parameters given

    public double calculateProbability() {
        return ( Math.pow(Math.E, _riskFreeRate * _deltaT) - _lowerFactor) / (_upperFactor - _lowerFactor);
    }
    // calculate equity prices on each node
    public void calculateEquityMovement() {
        // Set up matrix dimentions
        int _columns = ((int)_iteration) + 1;
        int _rows = (((int)_iteration) * 2) + 1;
        // Set up Matrix to hold price movements
        _equityMovement = new double[_rows][_columns];
        // Prepare pointers for calculations
        int _pointer = (int)_iteration;
        int _pointerUpper = 0;
        int _pointerLower = 0;
        int _columnPointer = 0;
        double _value = _assetPrice;
        // Initialise upper pointer with row number
        _pointerUpper = _pointer;
        while (true) {
            for (int i= _columnPointer; i < _columns; i++) {
                _equityMovement[_pointerUpper][i] = Double.valueOf(_decimalFormat.format(_value));
                i++;
            }
            _pointerUpper--;
            _columnPointer++;
            _value = Math.pow(_upperFactor, _columnPointer)*_assetPrice;
            if (_pointerUpper < 0) break;
        }
        _columnPointer = 1;
        _pointerLower = _pointer + 1;
        _value = Math.pow(_lowerFactor, _columnPointer)*_assetPrice;
        while (true) {
            for (int i= _columnPointer; i < _columns; i++) {
                _equityMovement[_pointerLower][i] = Double.valueOf(_decimalFormat.format(_value));
                i++;
            }
            if (_pointerLower == _rows) break;
            _pointerLower++;
            _columnPointer++;
            _value = Math.pow(_lowerFactor, _columnPointer)*_assetPrice;
        }
        //priceEuropeanPutOption(_equityMovement);
        // new BinomialTreeFrame(castArray(_equityMovement),(int)_iteration);
        //new BinomialTreeFrame(castArray(priceEuropeanPutOption(_strikePrice, _equityMovement)),(int) _iteration);
    }
    // price European put option
    public double[][] priceEuropeanPutOption() {
        calculateEquityMovement();
        int _rows = _equityMovement.length;
        int _columns = _equityMovement[1].length;
        _optionPriceMovement = new double[_rows][_columns];
        // At last column values are
        for (int i=0; i<_rows;i++) {
            if (_equityMovement[i][_columns-1] != 0) {
                _optionPriceMovement[i][_columns-1] = Math.max(_strikePrice-_equityMovement[i][_columns-1], 0);
            }
        }
        int pointer = _columns-2;
        while(true) {
            for (int i=0; i< _rows;i++) {
                if (_equityMovement[i][pointer] != 0) {
                    // get upper lower of next row
                    double upperNext = _optionPriceMovement[i-1][pointer+1];
                    double lowerNext = _optionPriceMovement[i+1][pointer+1];
                    _optionPriceMovement[i][pointer]
                            = Math.pow(Math.E, -(_riskFreeRate*_deltaT)) *((_probability*upperNext)+((
                            1-
                                    _probability)*lowerNext));
                }
            }
            if (pointer ==0) break;
            pointer--;
        }
        //new BinomialTreeFrame(castArray(_optionPriceMovement), (int)_iteration);
        return  _optionPriceMovement;
    }
    // return value located on first node (price of option)
    public double getEuropeanPutOptionRootPrice() {
        for (int i=0; i<_optionPriceMovement.length; i++) {
            if (_optionPriceMovement[i][0] != 0) {
                return _optionPriceMovement[i][0];
            }
        }
        return 0;
    }
    // calculate price of European Call Option
    public double[][] priceEuropeanCallOption() {
        calculateEquityMovement();
        int _rows = _equityMovement.length;
        int _columns = _equityMovement[1].length;
        _optionPriceMovement = new double[_rows][_columns];
        // At last column values are
        for (int i=0; i<_rows;i++) {
            if (_equityMovement[i][_columns-1] != 0) {
                _optionPriceMovement[i][_columns-1] = Math.max(_equityMovement[i][_columns-1]-_strikePrice, 0);
            }
        }
        int pointer = _columns-2;
        while(true) {
            for (int i=0; i< _rows;i++) {
                if (_equityMovement[i][pointer] != 0) {
                    // get upper lower of next row
                    double upperNext = _optionPriceMovement[i-1][pointer+1];
                    double lowerNext = _optionPriceMovement[i+1][pointer+1];
                    _optionPriceMovement[i][pointer]
                            = Math.pow(Math.E, -(_riskFreeRate*_deltaT)) *((_probability*upperNext)+((
                            1-
                                    _probability)*lowerNext));
                }
            }
            if (pointer ==0) break;
            pointer--;
        }
        //new BinomialTreeFrame(castArray(_optionPriceMovement), (int)_iteration);
        return  _optionPriceMovement;
    }
    public BinomialTreeFrame getEquityPriceMovementPanel() {
        return new BinomialTreeFrame(castArray(_equityMovement), (int) _iteration);
    }
    // return price held in first node (price of call option)
    public double getEuropeanCallOptionRootPrice() {
        for (int i=0; i<_optionPriceMovement.length; i++) {
            if (_optionPriceMovement[i][0] != 0) {
                return _optionPriceMovement[i][0];
            }
        }
        return 0;
    }
    // helper method that returns the number of rows
    // of a two dimentional array.
    private int getRowNumber(double[][] _array) {
        return _array.length;
    }
    // helper method that returns the number of columns
    // of a two dimentional array.
    private int getColumnNumber(double[][] _array) {
        return _array[0].length;
    }
    // calculate price of American Option
    // NEEDS REVISION !
    public double[][] priceAmericanPutOption() {
        // Generate EquityPriceMovement
        calculateEquityMovement();
        // Generate EuropeanPutOptionMatrix
        double[][] _europeanPutOptionMovement = priceEuropeanPutOption();
        int _rows = getRowNumber(_europeanPutOptionMovement);
        int _columns = getColumnNumber(_europeanPutOptionMovement);
        _americanPutOptionPriceMovement = new double[_rows][_columns];
        // Put last two column of europeanOptionPrices in new array
        for (int i=0; i < _rows; i++) {
            for (int j=0; j < _columns; j++) {
                _americanPutOptionPriceMovement[i][j] = _europeanPutOptionMovement[i][j];
            }
        }
        double _payoff = 0;
        double _optionPrice = 0;
        boolean flag = false;
        int pointer =0;
        double _value = 0;
        // NEEDS IMPRVEMENT
        _americanPutOptionPriceMovement[3][0]=0;
        for (int j=_columns-2; j > 0; j--) {
            for (int i=0; i<_rows-1;i++) {
                if (_equityMovement[i][j] != 0) {
                    _payoff = Math.max(_strikePrice - _equityMovement[i][j], 0);
                    _optionPrice = _americanPutOptionPriceMovement[i][j];
                    if (_payoff > _optionPrice) {
                        _americanPutOptionPriceMovement[i][j] = _payoff;
                        flag = true;
                    } else {
                        _americanPutOptionPriceMovement[i][j] = _optionPrice;
                    }
                }
            }
            if (flag) {
                for (int k=j-1; k < _rows-2; k++) {
                    _value = Math.pow(Math.E, (-_riskFreeRate)*_deltaT)
                            *( (_probability*_americanPutOptionPriceMovement[k][j])
                            +((1-_probability)*_americanPutOptionPriceMovement[k+2][j]));
                    _americanPutOptionPriceMovement[k+1][j-1]=_value;
                }
                flag=false;
            }
        }
        _payoff = Math.max(_strikePrice - _equityMovement[((_rows-1)/2)+1][1], 0);
        _optionPrice = _americanPutOptionPriceMovement[((_rows-1)/2)+1][1];
        _value = Math.pow(Math.E, (-_riskFreeRate)*_deltaT)
                *( (_probability*_americanPutOptionPriceMovement[2][1])
                +((1-_probability)*_americanPutOptionPriceMovement[4][1]));
        _americanPutOptionPriceMovement[0][0]=_value;
        // new BinomialTreeFrame(castArray(_optionPriceMovement), (int)_iteration);
        new BinomialTreeFrame(castArray(_americanPutOptionPriceMovement), (int)_iteration);
        return  _americanPutOptionPriceMovement;
    }
    public void reCalculate(int _columns, int _rows, double[][] _array) {
        int pointer = _columns-2;
        while(true) {
            for (int i=0; i< _rows;i++) {
                if (_equityMovement[i][pointer] != 0) {
                    // get upper lower of next row
                    double upperNext = _optionPriceMovement[i-1][pointer+1];
                    double lowerNext = _optionPriceMovement[i+1][pointer+1];
                    _optionPriceMovement[i][pointer]
                            = Math.pow(Math.E, -(_riskFreeRate*_deltaT)) *((_probability*upperNext)+((
                            1-
                                    _probability)*lowerNext));
                }
            }
            if (pointer ==0) break;
            pointer--;
        }
    }
    // Return array as an array of String values
    // that can be used by a JTable
    public String[][] castArray(double[][] _array) {
        int _rows = _array.length;
        int _columns = _array[1].length;
        String[][] _sArray = new String[_rows][_columns];
        for (int i=0; i < _rows; i++) {
            for (int j=0; j < _columns; j++) {
                if (_array[i][j] != 0) {
                    _sArray[i][j]=_array[i][j]+"";
                }
            }
        }
        return _sArray;
    }
    public static void main(String[] args) {
        //BinomialTree _binomialTree = new BinomialTree(7, 0.52916, 1.12240, 0.89095,  100, 100);
        //_binomialTree.toPrint();
        // _binomialTree.toPrint();
        //_binomialTree.calculateEuropeanPutOption();
        //            _binomialTree.expand();
        // BinomialTreeFrame btf = new BinomialTreeFrame(_binomialTree.obtainDataModel(), 5);
        // _binomialTree.obtainDataModel();
        BinomialTree t = new BinomialTree(100, 100, 0.04, 0.2, 1, 400);
        //t.calculateEquityMovement();
        //t.priceEuropeanPutOption();
        //t.priceEuropeanCallOption();
        t.priceEuropeanPutOption();
    }
}
