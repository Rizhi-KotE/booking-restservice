import React from 'react';

class Hello extends React.Component {
    render() {
        return (
            <h1>Hello {this.props.name}</h1>
        );
    }
}

React.render(<Hello name="Dima"/>, document.getElementById('root'));