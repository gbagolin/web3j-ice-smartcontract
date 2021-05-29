package ContractManagement;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3jSingleton {

    private static Web3j web3j = null;

    private static void setWeb3jSingleton() {
        web3j = Web3j.build(new HttpService("http://localhost:7545"));
    }

    public static Web3j getWeb3j() {
        if (web3j == null) {
            setWeb3jSingleton();
        }
        return web3j;
    }

}
