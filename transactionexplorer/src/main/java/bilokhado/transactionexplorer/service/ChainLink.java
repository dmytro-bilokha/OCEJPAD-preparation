package bilokhado.transactionexplorer.service;

import java.util.List;

/**
 * Interface of the ejb call chain link
 */
public interface ChainLink {

    public String getType();

    public void processChain(int baseId, int id, List<ChainLink> ejbChain, String[][] resultMatrix);

}
