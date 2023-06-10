$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editCourseForm').submit(function(e) {
    e.preventDefault();

    // Lấy thông tin khóa học từ form
    var courseId = $(this).attr('action').split('/').pop();
    var courseName = $('#name').val();
    var courseDescription = $('#description').val(); // Lấy giá trị của trường description

    // Tạo đối tượng khóa học mới
    var updatedCourse = {
      name: courseName,
      description: courseDescription // Thêm trường description
    };

    // Gửi yêu cầu PUT để cập nhật khóa học
    $.ajax({
      url: 'http://localhost:8080/api/v1/courses/' + courseId,
      type: 'PUT',
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(updatedCourse)
    })
      .then(function() {
        alert('Course updated successfully!');
        window.location.href = '/courses'; // Chuyển hướng về trang danh sách khóa học
      })
      .catch(function(xhr) {
        if (xhr.status === 200) {
          alert('Course updated successfully!');
          window.location.href = '/courses';
        }
      });
  });
});
