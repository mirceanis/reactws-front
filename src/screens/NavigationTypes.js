import React from 'react';
import {View, Text, StyleSheet, ScrollView} from 'react-native';
import Row from '../components/Row';
import { NativeModules } from 'react-native';

CamModule = NativeModules.CamModule

class NavigationTypes extends React.Component {

  constructor(props) {
    super(props);
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }

  onNavigatorEvent(event) {
    if (event.type === 'DeepLink') {
      const parts = event.link.split('/');
      if (parts[0] === 'tab1') {
        this.props.navigator.push({
          screen: parts[1]
        });
      }
    }
  };

  fuckOffMessage = async function() {
    console.log("fucking off");
    return new Promise(resolve => {
      console.log("fuck off");
      resolve("resolved to fuck off")
    })
  }

  reactSucks = async () => {
    console.log("it really does");
    console.log(await this.fuckOffMessage());
    CamModule.show("whatever");
  }

  reactSucksLater = async () => {
    console.log("It will suck a lot more later");
    console.log(await CamModule.resolvePromiseLater("sucking enough yet later?"));
  }

  checkApplicant = async () => {
    applicantId = await CamModule.checkApplicant();
    console.log("applicantID is: " + applicantId);
  }

  reactSucksNow = async () => {
    console.log("It sucks a lot now");
    console.log(await CamModule.resolvePromiseNow("sucking enough yet?"));
  }

  toggleDrawer = () => {
    this.props.navigator.toggleDrawer({
      side: 'left',
      animated: true
    });
  };

  pushScreen = () => {
    this.props.navigator.push({
      screen: 'example.Types.Push',
      title: 'New Screen',
    });
  };

  render() {
    return (
      <ScrollView style={styles.container}>
        <Row title={'React sucks'} onPress={this.reactSucks} />
        <Row title={'React sucks now'} onPress={this.reactSucksNow} />
        <Row title={'React sucks later'} onPress={this.reactSucksLater} />
        <Row title={'Check applicant'} onPress={this.checkApplicant} />
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  row: {
    height: 48,
    paddingHorizontal: 16,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    borderBottomWidth: 1,
    borderBottomColor: 'rgba(0, 0, 0, 0.054)',
  },
  text: {
    fontSize: 16,
  },
});

export default NavigationTypes;
