package protocol;

import server.Server;
import server.db.Node;
import server.entities.Driver;
import server.entities.Vehicle;
import services.CryptoServices;

public class Protocol {
    private Server server = new Server();
    private int responseINT = -1;
    private Node responseNo = null;
    private Boolean responseBool = false;
    private Vehicle responseVehicle = null;

    public void request(int type, Vehicle data) {
        switch (type) {
            case 0: {
                var input = CryptoServices.compress(data.toString());
                this.server.save(input);
                break;
            }
            case 1: {
                var renavamInString = String.valueOf(data.getRenavam());
                var input = CryptoServices.compress(renavamInString);
                this.server.remove(input);
                break;
            }
            case 2: {
                this.server.getAll();
                break;
            }
            case 3: {
                var renavamInString = String.valueOf(data.getRenavam());
                var renavamCompressed = CryptoServices.compress(renavamInString);
                this.server.remove(renavamCompressed);
                break;
            }
            case 4: {
                var licensePlateCompressed = CryptoServices.compress(data.getLicencePlate());
                responseNo = this.server.getByLicensePlate(licensePlateCompressed);
                break;
            }
            case 6: 
                // responseINT = this.server.getAllNodes();
                break;
            case 7: {
                var renavamInString = String.valueOf(data.getRenavam());
                var renavamCompressed = CryptoServices.compress(renavamInString);
                var vehicleCompressd = CryptoServices.compress(data.toString());
                responseBool = this.server.update(renavamCompressed, vehicleCompressd);
                break;
            }
            case 8: {
                var renavamString = String.valueOf(data.getRenavam());
                var renavamCompressed = CryptoServices.compress(renavamString);
                var vehicleCompressed =  this.server.getOne(renavamCompressed);
                if (vehicleCompressed.isEmpty()) {
                    responseVehicle = null;
                    break;
                }
                var vehicleDecompressed = CryptoServices.decompress(vehicleCompressed);
                var vehicleDatas = vehicleDecompressed.split("#");
                responseVehicle = new Vehicle(vehicleDatas[0], Long.parseLong(vehicleDatas[1]), vehicleDatas[2], vehicleDatas[3], new Driver(vehicleDatas[4], vehicleDatas[5]));
                break;
            }
        }
    }

    public int response() {
        return this.responseINT;
    }
    
    public Node responseNo() {
        return this.responseNo;
    }

    public Boolean responseBool() {
        return this.responseBool;
    }

    public Vehicle responseVehicle() {
        return this.responseVehicle;
    }

}