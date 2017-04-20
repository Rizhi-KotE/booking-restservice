document.addEventListener('DOMContentLoaded', function() {

    let CommentBox = React.createClass({
        render: function () {
            return (<div>Hello, world! I am a CommentBox.</div>);
        }
    });
    React.renderComponent(
        CommentBox({}),
        document.getElementById('content')
    );
});