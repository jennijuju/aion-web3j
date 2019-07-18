
import org.web3j.aion.VirtualMachine;
import org.web3j.aion.crypto.Ed25519KeyPair;
import org.web3j.aion.protocol.Aion;
import org.web3j.aion.tx.AionTransactionManager;
import org.web3j.aion.tx.gas.AionGasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;

public class Call {

//    private static String NODE_ENDPOINT = System.getenv("NODE_ENDPOINT");
//    private static String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    private static final Aion aion = Aion.build(new HttpService(""));

    private static final TransactionManager manager = new AionTransactionManager(
            aion, new Ed25519KeyPair(""), VirtualMachine.AVM
    );


    public static void contractCall() throws Exception {
        HelloAvm helloContract = HelloAvm.load("0xa02fdda364ca6572e9f5ed7038fc62cb4f0df2af64ff12a8bed6ddc4d05e16a9", aion, manager, AionGasProvider.INSTANCE);
        String result = helloContract.call_getString().send();
        System.out.println("Result: " + result);
    }

    public static void main(String [] args) throws Exception {
        contractCall();

    }
}
