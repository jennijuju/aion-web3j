package org.aion.web3;

import org.web3j.aion.VirtualMachine;
import org.web3j.aion.crypto.Ed25519KeyPair;
import org.web3j.aion.protocol.Aion;
import org.web3j.aion.tx.AionTransactionManager;
import org.web3j.aion.tx.gas.AionGasProvider;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;


public class Call {

    private static String NODE_ENDPOINT = System.getenv("NODE_ENDPOINT");
    private static String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    private static final Aion aion = Aion.build(new HttpService(NODE_ENDPOINT));

    private static final TransactionManager manager = new AionTransactionManager(
            aion, new Ed25519KeyPair(PRIVATE_KEY), VirtualMachine.AVM
    );


    public static void main(String [] args) throws Exception {
        final Counter counterContract = Counter.load("0xa031b3db158704f698d6126e1ee714d0ef39be312850a4bc9db5b7e06c107bd0", aion, manager, AionGasProvider.INSTANCE);
        Integer result = counterContract.call_getCount().send();
        System.out.println("Current count is: " + result);
    }
}