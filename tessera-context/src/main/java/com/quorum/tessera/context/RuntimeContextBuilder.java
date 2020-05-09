package com.quorum.tessera.context;

import com.quorum.tessera.config.keys.KeyEncryptor;
import com.quorum.tessera.encryption.KeyPair;
import com.quorum.tessera.encryption.PublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RuntimeContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeContextBuilder.class);

    private List<KeyPair> keys = new ArrayList<>();

    private KeyEncryptor keyEncryptor;

    private List<PublicKey> alwaysSendTo = new ArrayList<>();

    private List<URI> peers = new ArrayList<>();

    private URI fullKnow;

    private Client p2pClient;

    private URI p2pServerUri;

    private boolean remoteKeyValidation;

    private boolean disablePeerDiscovery;

    private boolean useWhiteList;

    private RuntimeContextBuilder() {}

    public static RuntimeContextBuilder create() {
        return new RuntimeContextBuilder();
    }

    public RuntimeContextBuilder withP2pServerUri(URI p2pServerUri) {
        this.p2pServerUri = p2pServerUri;
        return this;
    }

    public RuntimeContextBuilder withKeys(List<KeyPair> keys) {
        this.keys.addAll(keys);
        return this;
    }

    public RuntimeContextBuilder withKeyEncryptor(KeyEncryptor keyEncryptor) {
        this.keyEncryptor = keyEncryptor;
        return this;
    }

    public RuntimeContextBuilder withPeers(List<URI> peers) {
        this.peers.addAll(peers);
        return this;
    }

    public RuntimeContextBuilder withFullKnow(URI fullKnow) {
        this.fullKnow = fullKnow;
        return this;
    }

    public RuntimeContextBuilder withAlwaysSendTo(List<PublicKey> alwaysSendTo) {
        this.alwaysSendTo.addAll(alwaysSendTo);
        return this;
    }

    public RuntimeContextBuilder withP2pClient(Client p2pClient) {
        this.p2pClient = p2pClient;
        return this;
    }

    public RuntimeContextBuilder withRemoteKeyValidation(boolean remoteKeyValidation) {
        this.remoteKeyValidation = remoteKeyValidation;
        return this;
    }

    public RuntimeContextBuilder withDisablePeerDiscovery(boolean disablePeerDiscovery) {
        this.disablePeerDiscovery = disablePeerDiscovery;
        return this;
    }

    public RuntimeContextBuilder withUseWhiteList(boolean useWhiteList) {
        this.useWhiteList = useWhiteList;
        return this;
    }

    public RuntimeContext build() {

        LOGGER.debug("Building {}", this);

        Objects.requireNonNull(p2pServerUri, "No p2pServerUri provided. ");
        Objects.requireNonNull(keyEncryptor, "No key encryptor provided. ");
        Objects.requireNonNull(p2pClient, "No p2pClient provided. ");

        RuntimeContext instance =
                new DefaultRuntimeContext(
                        keys,
                        keyEncryptor,
                        alwaysSendTo,
                        peers,
                        fullKnow,
                        p2pClient,
                        remoteKeyValidation,
                        p2pServerUri,
                        disablePeerDiscovery,
                        useWhiteList);
        LOGGER.debug("Built {}", this);
        return instance;
    }
}
