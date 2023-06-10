$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editClassForm').submit(function(e) {
    e.preventDefault();

    // Lấy thông tin lớp học từ form
    var classId = $(this).attr('action').split('/').pop();
    var className = $('#name').val();
    var classDescription = $('#description').val();
    var teacherId = $('#teacher').val(); // Lấy ID của giáo viên

    // Tạo đối tượng lớp học mới
    var updatedClass = {
      className: className,
      classDescription: classDescription,
      teacher: { id: teacherId } // Thêm thông tin giáo viên vào đối tượng lớp học
    };

    // Gửi yêu cầu PUT để cập nhật lớp học
    $.ajax({
      url: 'http://localhost:8080/api/v1/classes/' + classId,
      type: 'PUT',
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(updatedClass)
    })
      .then(function() {
        alert('Class updated successfully!');
        window.location.href = '/classes'; // Chuyển hướng về trang danh sách lớp học
      })
      .catch(function(xhr) {
        if (xhr.status === 200) {
          alert('Class updated successfully!');
          window.location.href = '/classes'; // Chuyển hướng về trang danh sách lớp học
        } else {
          alert('Error: ' + xhr.responseJSON.message);
        }
      });
  });
});
