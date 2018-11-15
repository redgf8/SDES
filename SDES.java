import java.util.Arrays;

/**
 * @author Daniel Haluszka
 * SDES class to implement the SDES encryption/decryption algorithm.
 */
public class SDES {

    private boolean[] key;

    /**
     * @author Daniel Haluszka
     * Constructor for SDES class, instantiates key.
     */
    public SDES() {

        this.key = new boolean[10];

    }

    /**
     * @author Daniel Haluszka
     * @param inp A bit array represented as booleans, true=1, false=0.
     * @param epv An expansion and/or permutation and/or selection vector; all numbers in epv must be in the range 0..inp.length-1, i.e. they must be valid subscripts for inp.
     * @return The permuted/expanded/selected bit array, or null if there is an error.
     * @throws IllegalArgumentException
     * Expand and/or permute and/or select from the bit array, inp, producing an expanded/permuted/selected bit array. Use the expansion/permutation vector epv.
     */
    public boolean[] expPerm(boolean[] inp, int[] epv) throws IllegalArgumentException {

        boolean[] result = new boolean[epv.length];

        //loop through each value of epv
        for (int i = 0; i < epv.length; i++) {

            //check for invalid indices
            if (epv[i] > (inp.length - 1)) {

                throw new IllegalArgumentException("Value at index (" + i + ") of epv (epv[" + i + "] = " + epv[i] + ") is out of bounds for inp (Size of inp: " + inp.length + ")");

            }

            //if specified index is valid, assign current index of result to value of inp at specified index
            result[i] = inp[epv[i]];

        }

        return result;

    }

    /**
     * @author Daniel Haluszka
     * @param scanner The scanner with which input for the key is received.
     * Get a 10 bit key from the keyboard, such as 1010101010. Store it as an array of booleans in a field.
     */
    public void getKey10(java.util.Scanner scanner) {

        String input;
        int count = 0; //will increment each time an index of the key is set

        //loop until all 10 indexes of the key have been set
        while (count < 10) {

            System.out.println("Enter a 10-bit key: ");
            input = scanner.nextLine();
            //check for invalid length
            if (input.length() != 10) {

                System.out.println("Please ensure that the key is exactly 10 bits long.\n");

            } else {

                //convert input string to char array for easier access
                char[] inputArr = input.toCharArray();

                //loop through each value of input, setting each index of key to the appropriate value, incrementing count after each loop
                for (int i = 0; i < 10; i++) {

                    if (inputArr[i] == '1') {

                        this.key[i] = true;

                    } else if (inputArr[i] == '0') {

                        this.key[i] = false;

                    } else {

                        //if the current char is not 0 or 1, the input is not valid
                        System.out.println("Invalid input received. Please ensure that the key contains only zeros and ones.\n");
                        count = 0;
                        break;

                    }
                    
                    count++;

                }

            }

        }

    }

    /**
     * @author Daniel Haluszka
     * @param inp An array of bytes, hopefully storing the codes of printable characters.
     * @return The characters as a String.
     * Convert the given byte array to a String
     */
    public java.lang.String byteArrayToString(byte[] inp) {

        return new String(inp);

    }

    /**
     * @author Daniel Haluszka
     * @param inp The array of bits to permute.
     * @return A bit array which is the left half of the parameter, inp.
     * Left half of x, L(x)
     */
    public boolean[] lh(boolean[] inp) {

        return Arrays.copyOfRange(inp, 0, inp.length / 2);

    }

    /**
     * @author Daniel Haluszka
     * @param inp The array of bits to permute.
     * @return A bit array which is the right half of the parameter, inp.
     * Right half of x, R(x)
     */
    public boolean[] rh(boolean[] inp) {
        
        return Arrays.copyOfRange(inp, inp.length / 2, inp.length);

    }
    /**
     * @author Daniel Haluszka
     * @param x First of the two bit array operands of the XOR operation.
     * @param y Second of the two bit array operands of the XOR operation.
     * @return A bit array containing the result of a bitwise XOR operation performed on the two operands.
     * @throws IllegalArgumentException
     * Exclusive OR. x and y must have the same length. x xor y is the same as x != y
     */
    public boolean[] xor(boolean[] x, boolean[] y) throws IllegalArgumentException{

        //check for different lengths
        if (x.length != y.length) {

            throw new IllegalArgumentException("Operands of XOR are not the same length (Size of x: " + x.length + ", Size of y: " + y.length + ")");

        }

        boolean[] result = new boolean[x.length];

        //loop through each index of the operands and compare, set corresponding index of result to appropriate result based on comparison
        for (int i = 0; i < x.length; i++) {

            if (x[i] != y[i]) {

                result[i] = true;

            } else {

                result[i] = false;

            }

        }

        return result;

    }

    /**
     * @author Daniel Haluszka
     * @param x The bit array that will be the left half of the concatenated bit array
     * @param y The bit array that will be the right half of the concatenated bit array
     * @return The concatenation of x and y
     * Concatenate the two bit arrays, x || y
     */
    public boolean[] concat(boolean[] x, boolean[] y) {

        int newLength = x.length + y.length;
        boolean[] concat = new boolean[newLength];

        //loop through each index of the resulting bit array
        for (int i = 0; i < newLength; i++) {

            //if we're working in the left half of the result, set the current index to the corresponding index of x
            if (i < x.length) {

                concat[i] = x[i];

            } else { //if we're working in the right half of the result, set the current index to the corresponding index of y
                //(which is found by offsetting i by the length of x, or the number of indexes that have already been traversed)

                concat[i] = y[i - x.length];

            }

        }

        return concat;

    }

    /**
     * @author Daniel Haluszka
     * @param inp A bit array, max length is 8 bits
     * @return A byte representing the value stored in the input
     * @throws IllegalArgumentException
     * Convert the given bit array to a single byte
     */
    public byte bitArrayToByte(boolean[] inp) throws IllegalArgumentException {

        //check for invalid length
        if (inp.length > 8) {

            throw new IllegalArgumentException("Bit array is longer than 8 bits (Size of inp: " + inp.length + ")");

        }

        byte result = 0;

        //loop backwards through each index of the input, adding the appropriate power of two (which is stored in i) to the result
        for (int i = 0; i < inp.length; i++) {

            if (inp[(inp.length - 1) - i] == true) {

                result += Math.pow(2, i);

            }

        }

        return result;

    }

    /**
     * @author Daniel Haluszka
     * @param b The byte to be converted to a bit array
     * @param size The size of the resulting bit array. The operator >>> can be used for an unsigned right shift.
     * @return The resulting bit array from converting the given byte.
     * @throws IllegalArgumentException
     * Convert the given byte to a bit array, of the given size.
     */
    public boolean[] byteToBitArray(byte b, int size) throws IllegalArgumentException {

        //check for sizes that are not applicable to a byte
        if (size < 1 || size > 8) {

            throw new IllegalArgumentException("Specified size (" + size + ") is out of bounds (1-8)");

        }

        boolean[] result = new boolean[size];
        int remainder = b;
        int bitMask = 1;

        //loop through each bit in remainder by bitshifting to the right one position with each loop
        for (int i = 0; i < size; i++) {

            //perform bitwise AND on remainder and bitMask, this will produce a 1 if the current last bit of remainder is 1
            if ((remainder & bitMask) == 1) {

                //if the last bit of the remainder is one, set the respective bit in the resulting bit array
                //(which is kept track of with i) to 1, otherwise leave 0
                result[(result.length - 1) - i] = true;

            }

            //shift remainder 1 to the right to examine the next bit
            remainder = remainder >>> 1;

        }

        return result;

    }
    
	/**
	 * @author Zachary Reynolds
	 * x A bit array represented as booleans, true=1, false=0.
	 * k A bit array represented as booleans, true=1, false=0.
	 */
	
	public boolean[] f(boolean[] x, boolean[] k)
	{
		boolean[] feist = feistel(k, x);
		boolean[] left = lh(x);
		boolean[] right = rh(x);
		left = xor(feist, left);
		return concat(left, right);
	}
	
	/**
	 * @author Zachary Reynolds
	 * x A bit array represented as booleans, true=1, false=0.
	 * k A bit array represented as booleans, true=1, false=0.
	 */
	
	public boolean[] feistel(boolean[] k, boolean[] x)
	{
		boolean[] a;
		a = rh(x);
		int[] ep = {3,0,1,2,1,2,3,0};
		a = expPerm(a, ep);
		a = xor(a, k);
		boolean[] left = lh(a);
		left = s0(left);
		boolean[] right = rh(a);
		right = s1(right);
		boolean[] p = concat(left, right);
		int[] p4 = {1,3,2,0};
		boolean[] z = expPerm(p, p4);
		return z;
	}
	
	/**
	 * @author Zachary Reynolds
	 * a A bit array represented as booleans, true=1, false=0.
	 */
	
	public boolean[] s0 (boolean[] a)
	{
		boolean[] s = new boolean[2];
		if(a[0] == false)
		{
			if(a[1] == false)
			{
				if(a[2] == false)
				{
					if(a[3] == false)
					{
						//0000
						s[0] = false;
						s[1] = true;
						return s;
					}
					else
					{
						//0001
						s[0] = true;
						s[1] = true;
						return s;
					}
				}
				else
				{
					if(a[3] == false)
					{
						//0010
						s[0] = false;
						s[1] = false;
						return s;
					}
					else
					{
						//0011
						s[0] = true;
						s[1] = false;
						return s;
					}
				}
			}
			else {
				if(a[2] == false)
				{
					if(a[3] == false)
					{
						//0100
						s[0] = true;
						s[1] = true;
						return s;
					}
					else
					{
						//0101
						s[0] = false;
						s[1] = true;
						return s;
					}
				}
				else
				{
					if(a[3] == false)
					{
						//0110
						s[0] = true;
						s[1] = false;
						return s;
					}
					else
					{
						//0111
						s[0] = false;
						s[1] = false;
						return s;
					}
				}
			}
		}
			else
			{
				if(a[1] == false)
				{
					if(a[2] == false)
					{
						if(a[3] == false)
						{
							//1000
							s[0] = false;
							s[1] = false;
							return s;
						}
						else
						{
							//1001
							s[0] = true;
							s[1] = true;
							return s;
						}
					}
					else
					{
						if(a[3] == false)
						{
							//1010
							s[0] = true;
							s[1] = false;
							return s;
						}
						else
						{
							//1011
							s[0] = false;
							s[1] = true;
							return s;
						}
					}
				}
				else {
					if(a[2] == false)
					{
						if(a[3] == false)
						{
							//1100
							s[0] = false;
							s[1] = true;
							return s;
						}
						else
						{
							//1101
							s[0] = true;
							s[1] = true;
							return s;
						}
					}
					else
					{
						if(a[3] == false)
						{
							//1110
							s[0] = true;
							s[1] = true;
							return s;
						}
						else
						{
							//1111
							s[0] = true;
							s[1] = false;
							return s;
						}
					}
				}
			}
	}
	
	/**
	 * @author Zachary Reynolds
	 * a A bit array represented as booleans, true=1, false=0.
	 */
	
	public boolean[] s1 (boolean[] b)
	{
		boolean[] s = new boolean[2];
		if(b[0] == false)
		{
			if(b[1] == false)
			{
				if(b[2] == false)
				{
					if(b[3] == false)
					{
						//0000
						s[0] = false;
						s[1] = false;
						return s;
					}
					else
					{
						//0001
						s[0] = true;
						s[1] = false;
						return s;
					}
				}
				else
				{
					if(b[3] == false)
					{
						//0010
						s[0] = false;
						s[1] = true;
						return s;
					}
					else
					{
						//0011
						s[0] = false;
						s[1] = false;
						return s;
					}
				}
			}
			else {
				if(b[2] == false)
				{
					if(b[3] == false)
					{
						//0100
						s[0] = true;
						s[1] = false;
						return s;
					}
					else
					{
						//0101
						s[0] = false;
						s[1] = true;
						return s;
					}
				}
				else
				{
					if(b[3] == false)
					{
						//0110
						s[0] = true;
						s[1] = true;
						return s;
					}
					else
					{
						//0111
						s[0] = true;
						s[1] = true;
						return s;
					}
				}
			}
		}
			else
			{
				if(b[1] == false)
				{
					if(b[2] == false)
					{
						if(b[3] == false)
						{
							//1000
							s[0] = true;
							s[1] = true;
							return s;
						}
						else
						{
							//1001
							s[0] = true;
							s[1] = false;
							return s;
						}
					}
					else
					{
						if(b[3] == false)
						{
							//1010
							s[0] = false;
							s[1] = false;
							return s;
						}
						else
						{
							//1011
							s[0] = false;
							s[1] = true;
							return s;
						}
					}
				}
				else {
					if(b[2] == false)
					{
						if(b[3] == false)
						{
							//1100
							s[0] = false;
							s[1] = true;
							return s;
						}
						else
						{
							//1101
							s[0] = false;
							s[1] = false;
							return s;
						}
					}
					else
					{
						if(b[3] == false)
						{
							//1110
							s[0] = false;
							s[1] = false;
							return s;
						}
						else
						{
							//1111
							s[0] = true;
							s[1] = true;
							return s;
						}
					}
				}
			}
	}
}
    
    
