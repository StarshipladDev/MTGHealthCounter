import React from 'react';
import { StyleSheet, Text, View ,Button} from 'react-native';


export default class HealthCounter extends React.Component{
    constructor(props) {
        super(props);
        this.state = {health0:20,health1:20};
    }
    changeHealth(up, index) {
        switch (index) {
            case 0:

                if (up) {
                    this.setState({ health0: this.state.health0 + 1 });
                }
                
                else {
                    this.setState({ health0: this.state.health0 - 1 });
                }
                break;
            case 1:

                if (up) {
                    this.setState({ health1: this.state.health1 + 1 });
                }
                else {
                    this.setState({ health1: this.state.health1 - 1 });
                }
                break;
            default:
                break;
        }
        
    }
    render() {
        return <View>
            <Button onPress={() => { this.changeHealth(false, 0) }} title={"-"} />
            <Text style={{ transform: [{ rotate: "180deg" }] }}> Player 1: { this.state.health0}</Text>
            <Button onPress={() => { this.changeHealth(true, 0) }} title={"+"} />

            <Button color={"red"} onPress={() => { this.changeHealth(true, 1) }} title={"+"} />
            <Text> Player 2:  {this.state.health1}</Text>
            <Button color={"red"} onPress={() => { this.changeHealth(false, 1) }} title={"-"} />
        </View>

    }
}