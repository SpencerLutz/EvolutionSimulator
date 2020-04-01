class Brain {
    Axon[][][] axons; //array of axons with weights ([layer][receiving][sending])
    Neuron[][] hidden; //array of hidden layers ([layer][neuron])
    Brain(Axon[][][] tAxons, Neuron[][] tHidden){ //constructor if neurons and axons provided
        axons = tAxons;
        hidden = tHidden;
    }
    Brain(int iSize, int hSize, int hNums, int oSize){    //constructor to make random neurons
        axons = randAxons(iSize, hSize, hNums, oSize);  //and axons for the MLP
        hidden = randHiddens(hSize, hNums);
    }
    Neuron[] think(double[] input, Neuron[] output){ //calculates output values
        for(int i = 0; i < hidden.length; i++){ //for each hidden layer
            if(i == 0) for(int j = 0; j < hidden[i].length; j++) //if its the first layer
                hidden[i][j].calc(sum(input, axons[i][j])); //multiply input values
            else for(int j = 0; j < hidden.length; j++) //any other time
                hidden[i][j].calc(sum(hidden[i-1], axons[i][j]));   //multiply the previous
        }                                                           //hidden layer
        for(int i = 0; i < output.length; i++) //for each output neuron
            output[i].calc(sum(hidden[hidden.length-1], axons[axons.length-1][i]));
        return output; //multiply the last hidden layer with last axons and return
    }
    double sum(double[] a, Axon[] b){      //gets the sum total of all values feeding into
        int sum = 0;                       //a neuron, where a is a double[] of all values
        for(int k = 0; k < a.length; k++)  //in the previous layer, and b is an array of
            sum += a[k] * b[k].weight;     //all axons feeding from the a's to this neuron
        return sum+b[a.length].weight;     //apply bias
    }
    double sum(Neuron[] a, Axon[] b){      //gets the sum total of all values feeding into
        int sum = 0;                       //a neuron, where a is a Neuron[] of all neurons
        for(int k = 0; k < a.length; k++)  //in the previous layer, and b is an array of
            sum += a[k].value*b[k].weight; //all axons feeding from the a's to this neuron
        return sum+b[a.length].weight;     //apply bias
    }
    Axon[][][] randAxons(int iSize, int hSize, int hNums, int oSize){ //generates axons with
        Axon[][][] ret = new Axon[hNums+1][][];                       //random weights
        for(int i = 0; i < ret.length; i++) { //for each axon layer
            ret[i] = new Axon[i == hNums ? oSize : hSize][]; //if last, set ret[i] to a new
            for (int j = 0; j < ret[i].length; j++) {        //array of axons of size of output
                ret[i][j] = new Axon[i == 0 ? iSize+1 : hSize+1];//if first, set ret[i][j] to a new
                for (int k = 0; k < ret[i][j].length; k++)   //array of axons of size of input
                    ret[i][j][k] = new Axon(); //-1 to 1
            }
        }
        return ret;
    }
    Neuron[][] randHiddens(int size, int num){  //generates hidden neurons with random biases
        Neuron[][] ret = new Neuron[num][size]; //return array ([layers][neurons])
        for(int i = 0; i < num; i++) //for each layer
            for(int j = 0; j < size; j++) //for each neuron
                ret[i][j] = new Neuron(); //create neuron
        return ret;
    }
}
