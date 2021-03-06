package org.bitcoinj.params;

import org.bitcoinj.core.BitcoinSerializer;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.Utils;
import org.bitcoinj.net.discovery.HttpDiscovery;

import java.math.BigInteger;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkState;

public class MultiChainParams extends MainNetParams {

    /**
     * Constructor for MultiChain network parameters.
     *
     * @param blockHash String containing hash of genesis block
     * @param rawHex String containing hex representation of genesis block raw data
     */
    public MultiChainParams(String blockHash, String rawHex, int port, int addressHeader, long addressChecksum, long packetMagic) {
        super();
        maxTarget = new BigInteger("452305946836919597809536725434324863026383426491186282892576025006686863360");
        dnsSeeds = new String[] {""};
        httpSeeds = new HttpDiscovery.Details[] {};
        addrSeeds = new int[] {};
        checkpoints = new HashMap<>();
        this.port = port;
        this.addressHeader = addressHeader;
        this.addressChecksum = addressChecksum;
        this.packetMagic = packetMagic;
        acceptableAddressCodes = new int[] { addressHeader };

        // Create a copy of the Multichain network's genesis block and over-write the BitcoinJ created genesis block.
        byte[] payload = Utils.HEX.decode(rawHex);
        Context context = new Context(this);
        BitcoinSerializer bs = this.getSerializer(true);
        this.genesisBlock = bs.makeBlock(payload);
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals(blockHash),genesisHash);
    }

    private static MultiChainParams instance;

    /**
     * Convenience method to get singleton.  Not to be used for instantiation purposes.
     * @return
     */
    public static synchronized MultiChainParams get() {
        return instance;
    }

    /**
     * Get the singleton representing MultiChain network parameters.
     *
     * @param blockHash String containing hash of genesis block
     * @param rawHex String containing hex representation of genesis block raw data
     * @return
     */
    public static synchronized MultiChainParams get(String blockHash, String rawHex, int port, int addressHeader, long addressChecksum, long packetMagic) {
        if (instance == null) {
            instance = new MultiChainParams(blockHash, rawHex, port, addressHeader, addressChecksum, packetMagic);
        }
        return instance;
    }
}