import React from 'react';
import {View, Text} from 'react-native';

console.log("gigleeeeeee");

class FirstTabScreen extends React.Component {

  render() {
    console.log("wtf gigel");
    return (
      <View style={{ flex: 1, backgroundColor: 'red', alignItems: 'center', justifyContent: 'center' }}>
        <Text>Hello world!</Text>
      </View>
    );
  }
}

export default FirstTabScreen;