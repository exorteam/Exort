const defaultIcon = require('@/assets/comment-user/user.jpg');
export function associationIcon(filename) {
    return filename ? '/files/' + filename : defaultIcon;
}
export function userIcon(filename) {
    return filename ? '/files/' + filename : defaultIcon;
}
