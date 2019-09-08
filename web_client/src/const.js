const defaultAssociationIcon = require('@/assets/comment-user/user.jpg');
export function associationIcon(filename) {
    return filename ? '/files/' + filename : defaultAssociationIcon;
}
