$(document).ready(function() {
  $('.delete-assignment').click(function(e) {
    e.preventDefault(); // Ngăn chặn hành vi mặc định khi nhấp vào liên kết
    var assignmentId = $(this).data('assignment-id');
    deleteAssignment(assignmentId);
  });
});

function deleteAssignment(assignmentId) {
  var deleteUrl = "/api/v1/assignments/" + assignmentId;
  $.ajax({
    url: deleteUrl,
    type: 'DELETE',
    success: function() {
      // Xóa thành công
      console.log('Assignment deleted');
      // Thực hiện các hành động bổ sung sau khi xóa, nếu cần

      // Cập nhật giao diện (xóa hàng dòng trong bảng)
      $(`[data-assignment-id="${assignmentId}"]`).closest('tr').remove();
    },
    error: function() {
      // Xóa thất bại hoặc không tìm thấy assignment
      console.log('Failed to delete assignment');
    }
  });
}
