
import org.web3j.aion.VirtualMachine;
import org.web3j.aion.crypto.Ed25519KeyPair;
import org.web3j.aion.protocol.Aion;
import org.web3j.aion.tx.AionTransactionManager;
import org.web3j.aion.tx.gas.AionGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;

public class Send {

    private static String NODE_ENDPOINT = System.getenv("NODE_ENDPOINT");
    private static String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    private static final Aion aion = Aion.build(new HttpService(NODE_ENDPOINT));

    private static final TransactionManager manager = new AionTransactionManager(
            aion, new Ed25519KeyPair(PRIVATE_KEY), VirtualMachine.AVM
    );



    public static void contractTransaction() throws Exception {
        HelloAvm helloContract = HelloAvm.load("0xa0c2e1d268cbcec13c68e64ca1c2bc582ef72df06ac3daa8882a122bbd5b1dab", aion, manager, AionGasProvider.INSTANCE);
        String result = helloContract.call_getString().send();
        TransactionReceipt receipt = helloContract.send_setString("Hello Jennijuju.").send();
        System.out.println("Success? " + receipt.isStatusOK() + "\nTx Hash: " + receipt.getTransactionHash());

    }

    public static void main(String [] args) throws Exception {
        contractTransaction();

    }
}
