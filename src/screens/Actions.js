import React from 'react';
import { StyleSheet, ScrollView } from 'react-native';
import Row from '../components/Row';

class Actions extends React.Component {

  constructor(props) {
    super(props);

    this._fab = false;
    this._rightButton = null;
    this._contextualMenu = false;
    this._toggleTabs = 'shown';
    this._toggleNavBar = 'shown';
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }

  onNavigatorEvent(event) {
    if (event.id === 'contextualMenuDismissed') {
      this._contextualMenu = false;
    }
  }

  setTitle = () => {
    this.props.navigator.setTitle({
      title: 'New Title!',
    });
  };

  setSubtitle = () => {
    this.props.navigator.setSubTitle({
      subtitle: 'New SubTitle!',
      navigatorStyle: {
        navBarSubtitleColor: 'red'
      }
    });
  };

  toggleTabs = () => {
    const to = this._toggleTabs === 'shown' ? 'hidden' : 'shown';

    this.props.navigator.toggleTabs({
      to,
      animated: true,
    });
    this._toggleTabs = to;
  };

  setTabBadge = () => {
    this.props.navigator.setTabBadge({
      tabIndex: 1,
      badge: Math.floor(Math.random() * 20) + 1,
    });
  };

  switchToTab = () => {
    this.props.navigator.switchToTab({
      tabIndex: 0,
    });
  };

  toggleNavBar = () => {
    const to = this._toggleNavBar === 'shown' ? 'hidden' : 'shown';

    this.props.navigator.toggleNavBar({
      to,
      animated: true,
    });
    this._toggleNavBar = to;
  };

  showSnackbar = () => {
    this.props.navigator.showSnackbar({
      title: 'Woo Snacks!',
    });
  };

  toggleContextualMenu = () => {
    if (!this._contextualMenu) {
      this.props.navigator.showContextualMenu({
        rightButtons: [{
          title: 'Edit',
          icon: require('../../img/edit.png'),
        }, {
          title: 'Delete',
          icon: require('../../img/delete.png'),
        }],
        onButtonPressed: (index) => console.log(`Button ${index} tapped`)
      });
      this._contextualMenu = true;
    } else {
      this.props.navigator.dismissContextualMenu();
      this._contextualMenu = false;
    }

  };

  setButtons = () => {
    let title = '';

    if (!this._rightButton) {
      title = 'Hello';
    } else if (this._rightButton === 'Hello') {
      title = 'Its Me';
    }

    this.props.navigator.setButtons({
      rightButtons: [{
        title,
        id: 'topRight',
      }],
      animated: true,
    });
    this._rightButton = title;
  };


  render() {
    return (
      <ScrollView style={styles.container}>
        <Row title={'Set Title'} onPress={this.setTitle} />
        <Row title={'Set Subtitle'} onPress={this.setSubtitle} />
        <Row title={'Toggle Tabs'} onPress={this.toggleTabs} />
        <Row title={'Set Tab Badge'} onPress={this.setTabBadge} />
        <Row title={'Switch To Tab 0'} onPress={this.switchToTab} />
        <Row title={'Toggle Nav Bar'} onPress={this.toggleNavBar} />
        <Row title={'Show Snackbar'} onPress={this.showSnackbar} platform={'android'} />
        <Row title={'Toggle Contextual Menu'} onPress={this.toggleContextualMenu} platform={'android'} />
        <Row title={'Set Right Buttons'} onPress={this.setButtons} />
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {},
});

export default Actions;
