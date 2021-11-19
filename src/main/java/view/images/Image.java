package view.images;

public enum Image {

    MAIN_IMG("main.jpg"),
    PAC_MAN("pac.gif");

    private fr.r1r0r0.deltaengine.model.sprites.Image image;

    Image(String path) {
        try {
            String p = "/images/" + path;
            this.image = new fr.r1r0r0.deltaengine.model.sprites.Image(getClass().getResource(p).getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public fr.r1r0r0.deltaengine.model.sprites.Image getSprite() {
        return image;
    }
}
