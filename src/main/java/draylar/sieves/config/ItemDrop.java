package draylar.sieves.config;

public class ItemDrop {

    public String item;
    public String dropTable;

    public ItemDrop(String item, String dropTable) {
        this.item = item;
        this.dropTable = dropTable;
    }

    public String getItem() {
        return item;
    }

    public String getDropTable() {
        return dropTable;
    }
}
