package BuilderPatternExample;

/**
 * Computer - Product class using the Builder Pattern.
 *
 * The Builder Pattern separates the construction of a complex object
 * from its representation, allowing the same construction process to
 * create different configurations.
 *
 * The private constructor ensures objects can ONLY be built via the Builder.
 */
public class Computer {

    // Required attribute
    private final String cpu;

    // Optional attributes
    private final String ram;
    private final String storage;
    private final String gpu;
    private final boolean hasWifi;
    private final boolean hasBluetooth;
    private final String operatingSystem;

    // Private constructor - only accessible through the Builder
    private Computer(Builder builder) {
        this.cpu             = builder.cpu;
        this.ram             = builder.ram;
        this.storage         = builder.storage;
        this.gpu             = builder.gpu;
        this.hasWifi         = builder.hasWifi;
        this.hasBluetooth    = builder.hasBluetooth;
        this.operatingSystem = builder.operatingSystem;
    }

    // Getters
    public String getCpu()             { return cpu; }
    public String getRam()             { return ram; }
    public String getStorage()         { return storage; }
    public String getGpu()             { return gpu; }
    public boolean isHasWifi()         { return hasWifi; }
    public boolean isHasBluetooth()    { return hasBluetooth; }
    public String getOperatingSystem() { return operatingSystem; }

    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               "  CPU             : " + cpu + "\n" +
               "  RAM             : " + (ram != null ? ram : "Not specified") + "\n" +
               "  Storage         : " + (storage != null ? storage : "Not specified") + "\n" +
               "  GPU             : " + (gpu != null ? gpu : "Integrated") + "\n" +
               "  Wi-Fi           : " + (hasWifi ? "Yes" : "No") + "\n" +
               "  Bluetooth       : " + (hasBluetooth ? "Yes" : "No") + "\n" +
               "  Operating System: " + (operatingSystem != null ? operatingSystem : "None");
    }

    /**
     * Static nested Builder class.
     * Provides a fluent API for constructing Computer objects step-by-step.
     */
    public static class Builder {

        // Required field
        private final String cpu;

        // Optional fields with defaults
        private String ram             = "8GB";
        private String storage         = "256GB SSD";
        private String gpu             = null;
        private boolean hasWifi        = false;
        private boolean hasBluetooth   = false;
        private String operatingSystem = null;

        /**
         * Builder constructor requires the mandatory CPU field.
         * @param cpu The processor specification (required)
         */
        public Builder(String cpu) {
            if (cpu == null || cpu.isEmpty()) {
                throw new IllegalArgumentException("CPU is required to build a Computer.");
            }
            this.cpu = cpu;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this; // Return this for method chaining
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder hasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder hasBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Builder operatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        /**
         * Builds and returns a fully configured Computer instance.
         * @return A new Computer built from the current Builder state.
         */
        public Computer build() {
            return new Computer(this);
        }
    }
}
