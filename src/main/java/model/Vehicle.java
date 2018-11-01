package main.java.model;

import main.java.utility.Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Vehicle that gets from a CLSC to another. Used in the algorithm to find the shortest path that this vehicle can take between two nodes.
 */
public class Vehicle {

    // Represents the type of battery of the vehicle
    public enum BatteryType {
        NI_NH, LI_ION
    }
    // Holds the battery type of the vehicle
    private BatteryType fBatteryType;
    // Holds the remaining battery percentage
    private double fBatteryPercentage;
    // Holds the type of patient we are transporting
    private Patient.Type fPatientType;
    // Holds the battery consumption. Units are in % loss per minute
    private double fBatteryConsumption;
    // Holds if the vehicle can reach its destination
    private boolean fCanReachDestination;
    // Holds the time taken to reach its destination
    private int fTimeTaken;
    // Holds in chronological order which CLSCs the vehicle went to to reach the destination
    private List<CLSC> fPathTaken;

    /**
     * Constructor
     * @param patientType The patient type we are transporting.
     */
    private Vehicle(Patient.Type patientType) {
        fBatteryType = BatteryType.NI_NH;
        fBatteryPercentage = 100.00;
        fTimeTaken = 0;
        fPatientType = patientType;
        setBatteryConsumption(patientType, BatteryType.NI_NH);
        fPathTaken = new ArrayList<>();
        fCanReachDestination = false;
    }

    /**
     * Copy constructor
     * @param vehicle The vehicle we are copying.
     */
    private Vehicle(Vehicle vehicle) {
        fBatteryType = vehicle.fBatteryType;
        fBatteryPercentage = vehicle.fBatteryPercentage;
        fPatientType = vehicle.fPatientType;
        fBatteryConsumption = vehicle.fBatteryConsumption;
        fTimeTaken = vehicle.fTimeTaken;
        fPathTaken = new ArrayList<>(vehicle.fPathTaken);
        fCanReachDestination = vehicle.fCanReachDestination;
    }

    /**
     * Copies all the fields of an existing vehicle. Used in the recursive algorithm to avoid creating new objects.
     * @param vehicle The vehicle to copy
     */
    private void copy(Vehicle vehicle) {
        fBatteryType = vehicle.fBatteryType;
        fBatteryPercentage = vehicle.fBatteryPercentage;
        fPatientType = vehicle.fPatientType;
        fBatteryConsumption = vehicle.fBatteryConsumption;
        fTimeTaken = vehicle.fTimeTaken;
        fPathTaken = new ArrayList<>(vehicle.fPathTaken);
        fCanReachDestination = vehicle.fCanReachDestination;
    }

    private void setBatteryType(BatteryType batteryType) {
        fBatteryType = batteryType;
        setBatteryConsumption(fPatientType, batteryType);
    }

    /**
     * Charges the vehicle. Adds the 120 minutes to fTimeTaken and resets the battery percentage.
     */
    private void charge() {
        fTimeTaken += 120;
        fBatteryPercentage = 100.0;
    }

    /**
     * Resets the vehicle to prepare it for another trip
     */
    private void resetVehicle() {
        fTimeTaken = 0;
        fBatteryPercentage = 100.0;
    }

    /**
     * Finds the shortest path a vehicle can take between two nodes. This is the method called by the application
     * @param startPoint The starting CLSC
     * @param endPoint The destination CLSC
     * @param graph The graph that holds all CLSCs
     * @param patientType The type of patient we are transporting
     * @return The vehicle its state after the trip. If fCanReachDestination is false, then the vehicle cannot make the trip.
     */
    public static Vehicle getShortestPath(CLSC startPoint, CLSC endPoint, Graph graph, Patient.Type patientType) {
        HashMap<CLSC, Path> shortestPathsFromStart = Dijkstra.getAllShortestPaths(startPoint, graph);
        Vehicle vehicle = new Vehicle(patientType);
        vehicle.getShortestPath(startPoint, endPoint, graph);
        return vehicle;
    }


    /**
     * Called by getShortestPath to
     * @param startPoint
     * @param endPoint
     * @param graph
     */
    private void getShortestPath(CLSC startPoint, CLSC endPoint, Graph graph) {
        //HashMap<CLSC, Path> shortestPathsFromStart = getAllShortestPaths(startingPoint, graph);
        // Try to reach destination with the NI_NH battery
       // if (vehicle.tryToReachDestination(getShortestPathBetweenTwoNodes(startingPoint, finishPoint, shortestPathsFromStart))) {
        fPathTaken.add(startPoint);
        if (tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph)) {
            fCanReachDestination = true;
            return;
        }

        setBatteryType(Vehicle.BatteryType.LI_ION);
        resetVehicle();
        // If that didn't work, try reaching the destination with the LI_ION battery
        if (tryToReachDestination(startPoint, endPoint, startPoint, endPoint, graph)) {
            fCanReachDestination = true;
        }

        // If that also didn't work, then there is no way to reach the destination

    }

    public boolean tryToReachDestination(CLSC startPoint, CLSC endPoint, CLSC currentNode, CLSC targetNode, Graph graph) {
        System.out.println("tryToReachDestination(), currentNode = " + currentNode + " , targetNode = " + targetNode);

        if (currentNode.hasCharge() && currentNode != startPoint) {
            charge();
        }

        List<CLSC> pathToDestination = Dijkstra.getShortestPathBetweenTwoNodes(currentNode, targetNode, Dijkstra.getAllShortestPaths(currentNode, graph));
        Vehicle newVehicle = new Vehicle(this);
        if (newVehicle.reachWithoutStop(pathToDestination)) {
            copy(newVehicle);
            return true;
        }

        List<CLSC> CLSCWithTerminal = getReachableChargingTerminals(startPoint, graph);
        Vehicle bestVehiclePath = null;
        for (int i = 0; i < CLSCWithTerminal.size(); ++i) {
            Vehicle tempVehicle = new Vehicle(this);
            if (tempVehicle.tryToReachDestination(startPoint, endPoint, currentNode, CLSCWithTerminal.get(i), graph)) {
                if (tempVehicle.tryToReachDestination(startPoint, endPoint, CLSCWithTerminal.get(i), endPoint, graph))
                if (bestVehiclePath == null) {
                    bestVehiclePath = tempVehicle;
                }
                else {
                    if (tempVehicle.fTimeTaken < bestVehiclePath.fTimeTaken) {
                        bestVehiclePath = tempVehicle;
                    }
                }
            }
        }

        if (bestVehiclePath != null) {
            copy(bestVehiclePath);
            return true;
        }
        return false;
    }

    private boolean reachWithoutStop(List<CLSC> path) {
        for (int i = 0; i < path.size() - 1; ++i) {
            if (!tryToReachNode(path.get(i), path.get(i + 1))) {
                return false;
            }
        }
        //fPathTaken.add(path.get(path.size() - 1));
        return true;
    }

    private boolean tryToReachNode(CLSC startingNode, CLSC nodeToReach) {
        if (fBatteryPercentage - calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach)) >= 20.0) {
            fBatteryPercentage -= calculateBatteryUsage(Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach));
            fTimeTaken += Path.getDistanceBetweenTwoNodes(startingNode, nodeToReach);
            fPathTaken.add(nodeToReach);
            return true;
        }
        return false;
    }

    /**
     *
     * @param duration
     * @return
     */
    public double calculateBatteryUsage(Integer duration) {
        return duration * fBatteryConsumption;
    }

    /**
     * Sets the battery consumption of the vehicle in % loss per minute.
     * @param patientType The type of patient we are transporting
     * @param batteryType The type of battery we are using
     */
    private void setBatteryConsumption(Patient.Type patientType, BatteryType batteryType) {
        if (batteryType == BatteryType.NI_NH) {
            if (patientType == Patient.Type.LOW_RISK) {
                fBatteryConsumption = 6 / 60.0;
            }
            else if (patientType == Patient.Type.MEDIUM_RISK) {
                fBatteryConsumption = 12 / 60.0;
            }
            else {
                fBatteryConsumption = 48 / 60.0;
            }
        }
        else {
            if (patientType == Patient.Type.LOW_RISK) {
                fBatteryConsumption = 5 / 60.0;
            }
            else if (patientType == Patient.Type.MEDIUM_RISK) {
                fBatteryConsumption = 10 / 60.0;
            }
            else {
                fBatteryConsumption = 30 / 60.0;
            }
        }
    }

    /**
     * Finds all the charging terminals the vehicle can access from its current node and its state.
     * @param currentPosition The position the vehicle is at currently.
     * @param graph The graph representing all CLSCs
     * @return A list of all CLSCs with charging terminals the vehicle can reach.
     */
    private List<CLSC> getReachableChargingTerminals(CLSC currentPosition, Graph graph) {
        //Vehicle testVehicle = new Vehicle(this);
        List<CLSC> reachableTerminals = new ArrayList<>();
        for (int i = 1; i < graph.getClscWithTerminal().size(); ++i) {
            if (graph.getClscWithTerminal().get(i) == currentPosition) {
                break;
            }
            if (wentOverTerminalAlready(graph.getClscWithTerminal().get(i))) {
                break;
            }
            Vehicle newVehicle = new Vehicle(this);
            if (newVehicle.reachWithoutStop(Dijkstra.getShortestPathBetweenTwoNodes(currentPosition, graph.getClscWithTerminal().get(i), Dijkstra.getAllShortestPaths(currentPosition, graph)))) {
                reachableTerminals.add(graph.getClscWithTerminal().get(i));
            }
        }
        return reachableTerminals;
    }

    /**
     * Indicated wheather or not the vehicle already went over a certain charging terminal
     * @param targetNode Is the node we want to know if we went over already.
     * @return True if we went over the node already, false if not.
     */
    private boolean wentOverTerminalAlready(CLSC targetNode) {
        for (int i = 0; i < fPathTaken.size(); ++i) {
            if (fPathTaken.contains(targetNode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes important attributes of the vehicle and turns them into a string.
     * @return A string describing the vehicle's state.
     */
    @Override
    public String toString() {
        String result = "";
        if (fCanReachDestination) {
            result += "Le type de vehicle utilise est " + fBatteryType + ", le pourcentage de batterie restant est " + fBatteryPercentage + "%, le chemin" +
                    " utilise le plus court est " + fPathTaken + ", et la longueur de celui-ci est de " + fTimeTaken + " minutes";
        }
        else {
            result += "Desole, le transport est refuse";
        }
        return result;
    }




}
