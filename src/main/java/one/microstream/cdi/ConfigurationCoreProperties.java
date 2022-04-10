/*
 *    Copyright 2021 Otavio Santana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.microstream.cdi;

import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfigurationPropertyNames;
import org.eclipse.microprofile.config.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * The relation with the properties from Microstream docs:
 * https://docs.microstream.one/manual/storage/configuration/properties.html
 */
public enum ConfigurationCoreProperties {
    /**
     * The base directory of the storage in the file system. Default is "storage" in the working directory.
     */
    STORAGE_DIRECTORY("microstream.storage.directory", EmbeddedStorageConfigurationPropertyNames.STORAGE_DIRECTORY),
    /**
     * The live file system configuration. See storage targets configuration.
     */
    STORAGE_FILESYSTEM("microstream.storage.filesystem", EmbeddedStorageConfigurationPropertyNames.STORAGE_FILESYSTEM),
    /**
     * If configured, the storage will not delete files. Instead of deleting a file it will be moved to this directory.
     */
    DELETION_DIRECTORY("microstream.deletion.directory", EmbeddedStorageConfigurationPropertyNames.DELETION_DIRECTORY),
    /**
     * If configured, files that will get truncated are copied into this directory.
     */
    TRUNCATION_DIRECTORY("microstream.truncation.directory", EmbeddedStorageConfigurationPropertyNames.TRUNCATION_DIRECTORY),
    /**
     * The backup directory.
     */
    BACKUP_DIRECTORY("microstream.backup.directory", EmbeddedStorageConfigurationPropertyNames.BACKUP_DIRECTORY),
    /**
     * The backup file system configuration. See storage targets configuration.
     */
    BACKUP_FILESYSTEM("microstream.backup.filesystem", EmbeddedStorageConfigurationPropertyNames.BACKUP_FILESYSTEM),
    /**
     * The number of threads and number of directories used by the storage engine. Every thread has exclusive access
     * to its directory. Default is 1.
     */
    CHANNEL_COUNT("microstream.channel.count", EmbeddedStorageConfigurationPropertyNames.CHANNEL_COUNT),
    /**
     * Name prefix of the subdirectories used by the channel threads. Default is "channel_".
     */
    CHANNEL_DIRECTORY_PREFIX("microstream.channel.directory.prefix", EmbeddedStorageConfigurationPropertyNames.CHANNEL_DIRECTORY_PREFIX),
    /**
     * Name prefix of the storage files. Default is "channel_".
     */
    DATA_FILE_PREFIX("microstream.data.file.prefix", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_PREFIX),
    /**
     * Name suffix of the storage files. Default is ".dat".
     */
    DATA_FILE_SUFFIX("microstream.data.file.suffix", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_SUFFIX),
    /**
     * Name prefix of the storage transaction file. Default is "transactions_".
     */
    TRANSACTION_FILE_PREFIX("microstream.transaction.file.prefix", EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_PREFIX),
    /**
     * Name suffix of the storage transaction file. Default is ".sft".
     */
    TRANSACTION_FILE_SUFFIX("microstream.transaction.file.suffix", EmbeddedStorageConfigurationPropertyNames.TRANSACTION_FILE_SUFFIX),
    /**
     * The name of the dictionary file. Default is "PersistenceTypeDictionary.ptd".
     */
    TYPE_DICTIONARY_FILE_NAME("microstream.type.dictionary.file.name", EmbeddedStorageConfigurationPropertyNames.TYPE_DICTIONARY_FILE_NAME),
    /**
     * Name suffix of the storage rescue files. Default is ".bak".
     */
    RESCUED_FILE_SUFFIX("microstream.rescued.file.suffix", EmbeddedStorageConfigurationPropertyNames.RESCUED_FILE_SUFFIX),
    /**
     * Name of the lock file. Default is "used.lock".
     */
    LOCK_FILE_NAME("microstream.lock.file.name", EmbeddedStorageConfigurationPropertyNames.LOCK_FILE_NAME),
    /**
     * Interval for the housekeeping. This is work like garbage collection or cache checking. In combination with
     * houseKeepingNanoTimeBudget the maximum processor time for housekeeping work can be set. Default is 1 second.
     */
    HOUSEKEEPING_INTERVAL("microstream.housekeeping.interval", EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_INTERVAL),
    /**
     * Number of nanoseconds used for each housekeeping cycle. Default is 10 milliseconds = 0.01 seconds.
     */
    HOUSEKEEPING_TIME_BUDGET("microstream.housekeeping.time.budget", EmbeddedStorageConfigurationPropertyNames.HOUSEKEEPING_TIME_BUDGET),
    /**
     * Abstract threshold value for the lifetime of entities in the cache. Default is 1000000000.
     */
    ENTITY_CACHE_THRESHOLD("microstream.entity.cache.threshold", EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_THRESHOLD),
    /**
     * Timeout in milliseconds for the entity cache evaluator. If an entity wasn’t
     * accessed in this timespan it will be removed from the cache. Default is 1 day.
     */
    ENTITY_CACHE_TIMEOUT("microstream.entity.cache.timeout", EmbeddedStorageConfigurationPropertyNames.ENTITY_CACHE_TIMEOUT),
    /**
     * Minimum file size for a data file to avoid cleaning it up. Default is 1024^2 = 1 MiB.
     */
    DATA_FILE_MINIMUM_SIZE("microstream.data.file.minimum.size", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_SIZE),
    /**
     * Maximum file size for a data file to avoid cleaning it up. Default is 1024^2*8 = 8 MiB.
     */
    DATA_FILE_MAXIMUM_SIZE("microstream.data.file.maximum.size", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MAXIMUM_SIZE),
    /**
     * The ratio (value in ]0.0;1.0]) of non-gap data contained in a storage file to prevent the file from being
     * dissolved. Default is 0.75 (75%).
     */
    DATA_FILE_MINIMUM_USE_RATIO("microstream.data.file.minimum.use.ratio", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_MINIMUM_USE_RATIO),
    /**
     * A flag defining whether the current head file (the only file actively written to)
     * shall be subjected to file cleanups as well.
     */
    DATA_FILE_CLEANUP_HEAD_FILE("microstream.data.file.cleanup.head.file", EmbeddedStorageConfigurationPropertyNames.DATA_FILE_CLEANUP_HEAD_FILE),
    /**
     * Allow custom properties in through Microprofile, using this prefix. E.g.:
     * If you want to include the "custom.test" property, you will set it as "microstream.property.custom.test"
     */
    CUSTOM("microstream.property", "");

    private final String microprofile;

    private final String microstream;

    ConfigurationCoreProperties(String microprofile, String microstream) {
        this.microprofile = microprofile;
        this.microstream = microstream;
    }

    public String getMicroprofile() {
        return microprofile;
    }

    public String getMicrostream() {
        return microstream;
    }

    /**
     * Check if there is a relation between the Microstream and Microstream properties.
     * If not, it is because it is a custom property.
     *
     * @return true if miscrostream is {@link String#isBlank()}
     */
    public boolean isCustom() {
        return this.microstream.isBlank();
    }

    /**
     * Return true if there is a relation between the Microstream and Microstream properties.
     *
     * @return the positive of {@link ConfigurationCoreProperties#isCustom()}
     */
    boolean isMapped() {
        return !isCustom();
    }

    public static Map<String, String> getProperties(Config config) {
        Map<String, String> properties = new HashMap<>();

        Arrays.stream(ConfigurationCoreProperties.values())
                .filter(ConfigurationCoreProperties::isMapped)
                .forEach(p -> addProperty(config, properties, p));

        StreamSupport
                .stream(config.getPropertyNames().spliterator(), false)
                .filter(k -> k.contains(CUSTOM.getMicroprofile()))
                .forEach(k -> {
                    String key = k.split(CUSTOM.getMicroprofile() + ".")[1];
                    String value = config.getValue(k, String.class);
                    properties.put(key, value);
                });

        return properties;
    }

    private static void addProperty(Config config, Map<String, String> properties,
                                    ConfigurationCoreProperties property) {
        Optional<String> optional = config.getOptionalValue(property.getMicroprofile(), String.class);
        optional.ifPresent(v -> properties.put(property.getMicrostream(), v));
    }
}
