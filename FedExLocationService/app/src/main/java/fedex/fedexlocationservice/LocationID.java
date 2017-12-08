package fedex.fedexlocationservice;



public class LocationID {

    public String getLocationID(String campaignID) {
        switch(campaignID) {
            case "5634472569470976": return "test_success_1";
            case "5654313976201216" : return "test_success_2";
            case "5682617542246400" : return "test_success_3";
            default : return "Location not found!";
        }
    }

    // Use this instead of the above if you want to read from an XML file in node format
    // <campaign campaignID="myID">
    //     <locationID> locID </locationID>
    // </campaign>
    // This requires file to be loaded onto device

    /*public String getLocationID(String campaignID) {
        try {
            File xmlFile = new File("PATH_TO_FILE");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("campaign");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("campaignID") == campaignID) {
                        return eElement.getElementsByTagName("locationID").item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            return "An error occurred";
        }
    }*/
}
