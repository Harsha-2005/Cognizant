package BuilderPatternExample;

/**
 * Main - Tests the Builder Pattern with different Computer configurations.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern - Computer Configuration Demo ===\n");

        // Configuration 1: High-end gaming PC
        Computer gamingPC = new Computer.Builder("Intel Core i9-14900K")
                .ram("64GB DDR5")
                .storage("2TB NVMe SSD")
                .gpu("NVIDIA RTX 4090")
                .hasWifi(true)
                .hasBluetooth(true)
                .operatingSystem("Windows 11 Pro")
                .build();

        System.out.println("--- Gaming PC ---");
        System.out.println(gamingPC);

        // Configuration 2: Budget office laptop (minimal options)
        Computer officeLaptop = new Computer.Builder("Intel Core i5-1235U")
                .ram("16GB DDR4")
                .storage("512GB SSD")
                .hasWifi(true)
                .operatingSystem("Windows 11 Home")
                .build();

        System.out.println("\n--- Office Laptop ---");
        System.out.println(officeLaptop);

        // Configuration 3: Developer workstation
        Computer devWorkstation = new Computer.Builder("AMD Ryzen 9 7950X")
                .ram("128GB DDR5")
                .storage("4TB NVMe SSD")
                .gpu("NVIDIA RTX 3060")
                .hasWifi(true)
                .hasBluetooth(true)
                .operatingSystem("Ubuntu 24.04 LTS")
                .build();

        System.out.println("\n--- Developer Workstation ---");
        System.out.println(devWorkstation);

        // Configuration 4: Bare-minimum server (no OS, no wireless)
        Computer server = new Computer.Builder("AMD EPYC 9654")
                .ram("512GB ECC RAM")
                .storage("8TB RAID SSD Array")
                .build();

        System.out.println("\n--- Bare-Metal Server ---");
        System.out.println(server);
    }
}
