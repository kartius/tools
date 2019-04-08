package com.kartius.kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreType;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueryableStoreFetcher {
    @Retryable(value = InvalidStateStoreException.class, backoff = @Backoff(delay = 1500), maxAttempts = 12)
    public <K, V> ReadOnlyKeyValueStore getQueryableStore(final String storeName, QueryableStoreType<ReadOnlyKeyValueStore<K, V>> queryableStoreType,
                                                          final KafkaStreams streams) {
        log.debug("--- Streams state {}", streams.state());
        log.debug("Get queryableStore {}", storeName);
        return streams.store(storeName, queryableStoreType);
    }
}
