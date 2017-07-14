import React, { Component } from 'react';
import { View, Text } from 'react-native';

export default class UPortNativeWrapperDemo extends Component {
    static propTypes = {
        title: React.PropTypes.string,
    };
    static defaultProps = {
        title: 'UPortNativeWrapperDemo',
    };

    render() {
        return (
            <View>
                <Text>{this.props.title}</Text>
                <Text>Demo native stuff here.</Text>

            </View>
        )
    }
}