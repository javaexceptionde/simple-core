package dev.jbull.corev.inventory.anvil;

public enum AnvilSlot {

    INPUT_LEFT(0),
    INPUT_RIGHT(1),
    OUTPUT(2);

    private int slot;

    private AnvilSlot(int slot){
        this.slot = slot;
    }

    public int getSlot(){
        return slot;
    }

    public static AnvilSlot bySlot(int slot){
        for(AnvilSlot anvilSlot : values()){
            if(anvilSlot.getSlot() == slot){
                return anvilSlot;
            }
        }

        return null;
    }
}
